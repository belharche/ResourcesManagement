package arimayichallenge.domain.resource;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "resources")
@NoArgsConstructor
@Getter
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceStatus status = ResourceStatus.ENABLED;

    public Resource(String name) {
        this.name = name;
    }

    public void disable() {
        this.status = ResourceStatus.DISABLED;
    }

    public void enable() {
        this.status = ResourceStatus.DISABLED;
    }

    public boolean isEnabled() {
        return status == ResourceStatus.ENABLED;
    }
}
