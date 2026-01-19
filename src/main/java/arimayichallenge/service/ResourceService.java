package arimayichallenge.service;

import arimayichallenge.domain.resource.Resource;
import arimayichallenge.exception.NotFoundException;
import arimayichallenge.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public Resource create(String name) {
        return resourceRepository.save(new Resource(name));
    }

    public Resource get(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resource not found"));
    }

    public List<Resource> getAll() {
        return resourceRepository.findAll();
    }

    public void disable(Long id) {
        Resource resource = get(id);
        resource.disable();
        resourceRepository.save(resource);
    }

    public void enable(Long id) {
        Resource resource = get(id);
        resource.enable();
        resourceRepository.save(resource);
    }
}
