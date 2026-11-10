package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCollectionId(Long id);
}
