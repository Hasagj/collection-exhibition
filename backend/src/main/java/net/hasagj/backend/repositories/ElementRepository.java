package net.hasagj.backend.repositories;

import net.hasagj.backend.dto.ElementResponse;
import net.hasagj.backend.models.Element;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElementRepository extends JpaRepository<Element, Long> {
    List<Element> findAllByCollectionId(Long collectionId);
    Optional<Element> findByIdAndCollectionId(Long id, Long collectionId);
}
