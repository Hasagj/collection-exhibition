package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Shelf;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    @EntityGraph(attributePaths = {"placement"})
    List<Shelf> findAllByCollectionId(Long collectionId);
    Optional<Shelf> findByIdAndCollectionId(Long id, Long collectionId);
    @EntityGraph(attributePaths = {"innerPlacements"})
    Optional<Shelf> findWithInnerPlacementsByIdAndCollectionId(Long id, Long collectionId);
}
