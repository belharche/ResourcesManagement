package arimayichallenge.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateResourceRequest(
        @NotBlank
        String name
) {
}
