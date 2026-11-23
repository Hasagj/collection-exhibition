package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByCollectionId(Long collectionId);
    Optional<Photo> findByPathAndCollectionId(String path, Long collectionId);
    boolean existsByPathAndCollectionId(String path, Long collectionId);
}
