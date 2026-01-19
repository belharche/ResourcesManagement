package arimayichallenge.dto.response;

import arimayichallenge.domain.resource.Resource;
import arimayichallenge.domain.resource.ResourceStatus;

public record ResourceResponse(
        Long id,
        String name,
        ResourceStatus status
) {
    public static ResourceResponse from(Resource resource) {
        return new ResourceResponse(
                resource.getId(),
                resource.getName(),
                resource.getStatus()
        );
    }
}
