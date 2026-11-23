package net.hasagj.backend.repositories;

import net.hasagj.backend.dto.CardDto;
import net.hasagj.backend.models.Card;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<CardDto.SimpleResponse> findAllByCollectionId(Long collectionId);
    Optional<Card> findByIdAndCollectionId(Long id, Long collectionId);
    @EntityGraph(attributePaths = {"photos"})
    Optional<Card> findWithPhotosByIdAndCollectionId(Long id, Long collectionId);
}
