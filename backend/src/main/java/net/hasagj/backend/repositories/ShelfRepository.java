package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    List<Shelf> findByCollectionId(Long id);
}
