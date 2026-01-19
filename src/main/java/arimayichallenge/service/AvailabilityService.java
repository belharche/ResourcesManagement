package arimayichallenge.service;

import arimayichallenge.domain.reservation.ReservationStatus;
import arimayichallenge.domain.resource.Resource;
import arimayichallenge.exception.NotFoundException;
import arimayichallenge.repository.ReservationRepository;
import arimayichallenge.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final ReservationRepository reservationRepository;
    private final ResourceRepository resourceRepository;

    public boolean isAvailable(Long resourceId, LocalDateTime start, LocalDateTime end) {

        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        if (!resource.isEnabled()) {
            return false;
        }

        return reservationRepository
                .findOverlappingReservations(resourceId, start, end, ReservationStatus.CREATED)
                .isEmpty();
    }
}

