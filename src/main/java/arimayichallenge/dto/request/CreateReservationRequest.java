package arimayichallenge.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateReservationRequest(
        @NotNull
        Long userId,

        @NotNull
        Long resourceId,

        @NotNull
        LocalDateTime startDateTime,

        @NotNull
        LocalDateTime endDateTime
) {
}
