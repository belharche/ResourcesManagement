package arimayichallenge.reservation;

import arimayichallenge.domain.reservation.Reservation;
import arimayichallenge.domain.resource.Resource;
import arimayichallenge.domain.user.User;
import arimayichallenge.exception.ConflictException;
import arimayichallenge.repository.ResourceRepository;
import arimayichallenge.repository.UserRepository;
import arimayichallenge.service.AvailabilityService;
import arimayichallenge.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;

    @Autowired
    AvailabilityService availabilityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Test
    void shouldRejectOverlappingReservation() {

        User user = userRepository.save(new User("Alice", "alice@test.com"));
        Resource resource = resourceRepository.save(new Resource("Room A"));

        reservationService.create(
                user.getId(),
                resource.getId(),
                LocalDateTime.of(2026, 1, 20, 10, 0),
                LocalDateTime.of(2026, 1, 20, 12, 0)
        );

        assertThatThrownBy(() ->
                reservationService.create(
                        user.getId(),
                        resource.getId(),
                        LocalDateTime.of(2026, 1, 20, 11, 0),
                        LocalDateTime.of(2026, 1, 20, 13, 0)
                )
        ).isInstanceOf(ConflictException.class);
    }


    @Test
    void shouldCancelReservation() {

        User user = userRepository.save(new User("Bob", "bob@test.com"));
        Resource resource = resourceRepository.save(new Resource("Room B"));

        Reservation reservation = reservationService.create(
                user.getId(),
                resource.getId(),
                LocalDateTime.of(2026, 1, 20, 10, 0),
                LocalDateTime.of(2026, 1, 20, 12, 0)
        );

        reservationService.cancel(reservation.getId());

        boolean available = availabilityService.isAvailable(
                resource.getId(),
                LocalDateTime.of(2026, 1, 20, 10, 0),
                LocalDateTime.of(2026, 1, 20, 12, 0)
        );

        assertThat(available).isTrue();
    }


    @Test
    void shouldRejectInvalidTimeRange() {

        User user = userRepository.save(new User("Tim", "tim@test.com"));
        Resource resource = resourceRepository.save(new Resource("Room D"));

        assertThatThrownBy(() ->
                reservationService.create(
                        user.getId(),
                        resource.getId(),
                        LocalDateTime.of(2026, 1, 20, 12, 0),
                        LocalDateTime.of(2026, 1, 20, 10, 0)
                )
        ).isInstanceOf(ConflictException.class);
    }


}
