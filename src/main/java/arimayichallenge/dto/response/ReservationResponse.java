package arimayichallenge.dto.response;

import arimayichallenge.domain.reservation.Reservation;
import arimayichallenge.domain.reservation.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        Long userId,
        Long resourceId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        ReservationStatus status
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getResource().getId(),
                reservation.getStartDateTime(),
                reservation.getEndDateTime(),
                reservation.getStatus()
        );
    }
}
