package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
