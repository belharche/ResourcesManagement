package arimayichallenge.repository;

import arimayichallenge.domain.resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
