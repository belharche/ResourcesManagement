package arimayichallenge.domain.reservation;

import arimayichallenge.domain.resource.Resource;
import arimayichallenge.domain.user.User;
import arimayichallenge.exception.ConflictException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.CREATED;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Resource resource;


    public Reservation(User user, Resource resource, LocalDateTime start, LocalDateTime end) {

        if (!start.isBefore(end)) {
            throw new ConflictException("Invalid time range");
        }

        this.user = user;
        this.resource = resource;
        this.startDateTime = start;
        this.endDateTime = end;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }
}