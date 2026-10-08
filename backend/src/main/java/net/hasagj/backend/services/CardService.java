package net.hasagj.backend.services;

import net.hasagj.backend.models.Card;
import net.hasagj.backend.repositories.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public Card create(Card card) {
        return repository.save(card);
    }
}
