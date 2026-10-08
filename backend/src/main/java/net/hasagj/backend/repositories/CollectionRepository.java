package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
}
