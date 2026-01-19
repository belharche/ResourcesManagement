package arimayichallenge.reservation;

import arimayichallenge.domain.resource.Resource;
import arimayichallenge.domain.user.User;
import arimayichallenge.exception.ConflictException;
import arimayichallenge.repository.ResourceRepository;
import arimayichallenge.repository.UserRepository;
import arimayichallenge.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ReservationConcurrencyTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Test
    void shouldPreventConcurrentOverlappingReservations() throws Exception {

        User user = userRepository.save(new User("John Doe", "john@test.com"));
        Resource resource = resourceRepository.save(new Resource("Meeting Room A"));

        LocalDateTime start = LocalDateTime.of(2026, 1, 20, 10, 0);
        LocalDateTime end   = LocalDateTime.of(2026, 1, 20, 12, 0);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch startLatch = new CountDownLatch(1);

        Callable<Boolean> task = () -> {
            startLatch.await();
            try {
                reservationService.create(user.getId(), resource.getId(), start, end);
                return true;
            } catch (ConflictException e) {
                return false;
            }
        };

        Future<Boolean> f1 = executor.submit(task);
        Future<Boolean> f2 = executor.submit(task);

        startLatch.countDown();

        long successCount = (f1.get() ? 1 : 0) + (f2.get() ? 1 : 0);

        executor.shutdown();

        assertThat(successCount).isEqualTo(1);
    }
}