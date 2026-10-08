package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
