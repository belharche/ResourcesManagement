package arimayichallenge.controller;

import arimayichallenge.domain.reservation.Reservation;
import arimayichallenge.dto.request.CreateReservationRequest;
import arimayichallenge.dto.response.ReservationResponse;
import arimayichallenge.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestBody @Valid CreateReservationRequest request
    ) {
        Reservation reservation = reservationService.create(
                request.userId(),
                request.resourceId(),
                request.startDateTime(),
                request.endDateTime()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ReservationResponse.from(reservation));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}