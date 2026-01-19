package arimayichallenge.controller;

import arimayichallenge.domain.resource.Resource;
import arimayichallenge.dto.request.CreateResourceRequest;
import arimayichallenge.dto.response.ResourceResponse;
import arimayichallenge.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<ResourceResponse> create(
            @RequestBody @Valid CreateResourceRequest request
    ) {
        Resource resource = resourceService.create(request.name());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResourceResponse.from(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResourceResponse.from(resourceService.get(id))
        );
    }

    @GetMapping
    public ResponseEntity<List<ResourceResponse>> getAll() {
        return ResponseEntity.ok(
                resourceService.getAll()
                        .stream()
                        .map(ResourceResponse::from)
                        .toList()
        );
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disable(@PathVariable Long id) {
        resourceService.disable(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<Void> enable(@PathVariable Long id) {
        resourceService.enable(id);
        return ResponseEntity.noContent().build();
    }
}