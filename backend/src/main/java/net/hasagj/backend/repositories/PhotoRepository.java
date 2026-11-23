package net.hasagj.backend.repositories;

import net.hasagj.backend.dto.PhotoDto;
import net.hasagj.backend.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<PhotoDto.SimpleResponse> findAllByCollectionId(Long collectionId);
    Optional<Photo> findByPathAndCollectionId(String path, Long collectionId);
    Optional<Photo> findByPathAndCardId(String path, Long cardId);
    Optional<Photo> findByPathAndGroupId(String path, Long groupId);
}
