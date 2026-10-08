package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
}
