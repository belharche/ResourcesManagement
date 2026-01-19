package arimayichallenge.repository;

import arimayichallenge.domain.reservation.Reservation;
import arimayichallenge.domain.reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
        select r
        from Reservation r
        where r.resource.id = :resourceId
          and r.status = :status
          and r.startDateTime < :end
          and r.endDateTime > :start
    """)
    List<Reservation> findOverlappingReservations(
            Long resourceId,
            LocalDateTime start,
            LocalDateTime end,
            ReservationStatus status
    );
}
