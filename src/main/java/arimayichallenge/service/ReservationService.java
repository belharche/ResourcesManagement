package arimayichallenge.service;

import arimayichallenge.domain.reservation.Reservation;
import arimayichallenge.domain.reservation.ReservationStatus;
import arimayichallenge.domain.resource.Resource;
import arimayichallenge.domain.user.User;
import arimayichallenge.exception.ConflictException;
import arimayichallenge.exception.NotFoundException;
import arimayichallenge.repository.ReservationRepository;
import arimayichallenge.repository.ResourceRepository;
import arimayichallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    @Transactional
    public Reservation create(Long userId, Long resourceId, LocalDateTime start, LocalDateTime end) {

        if (!start.isBefore(end)) {
            throw new ConflictException("Invalid time range");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Resource resource = resourceRepository.findByIdForUpdate(resourceId)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        if (!resource.isEnabled()) {
            throw new ConflictException("Resource is disabled");
        }

        List<Reservation> conflicts =
                reservationRepository.findOverlappingReservations(
                        resourceId,
                        start,
                        end,
                        ReservationStatus.CREATED
                );

        if (!conflicts.isEmpty()) {
            throw new ConflictException("Resource already reserved for this time range");
        }

        Reservation reservation =
                new Reservation(user, resource, start, end);

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void cancel(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        reservation.cancel();
    }
}
