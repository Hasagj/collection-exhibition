package net.hasagj.backend.repositories;

import net.hasagj.backend.dto.CollectionDto;
import net.hasagj.backend.models.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<CollectionDto.SimpleResponse> findAllProjectedBy();
}
