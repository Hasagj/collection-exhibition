package net.hasagj.backend.services;

import net.hasagj.backend.models.Card;
import net.hasagj.backend.models.Collection;
import net.hasagj.backend.models.Photo;
import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.repositories.CardRepository;
import net.hasagj.backend.util.Coordinates;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository repository;

    public CardService(CardRepository repository) { this.repository = repository; }

    public Card create(String name, @Nullable String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        name = name == null || name.isBlank() ? "Untitled" : name.trim();
        description = description == null || description.isBlank() ? null : description;
        Card card = new Card(name, description, collection, shelf, coordinates);
        return repository.save(card);
    }

    public Card create(String name, String description, Collection collection, Shelf shelf, float x, float y) {
        name = name == null || name.isBlank() ? "Untitled" : name.trim();
        description = description == null || description.isBlank() ? null : description;
        Card card = new Card(name, description, collection, shelf, x, y);
        return repository.save(card);
    }

    public List<Card> getAll(Long collectionId) { return repository.findByCollectionId(collectionId); }

    public Optional<Card> get(Long id) { return repository.findById(id); }

    public Optional<Card> update(Long id, String name, String description) {
        Optional<Card> card = repository.findById(id);
        if (card.isPresent()) {
            name = name == null || name.isBlank() ? card.get().getName() : name;
            description = description == null ? card.get().getDescription() : description;
            card.get().setName(name);
            card.get().setDescription(description);
            return Optional.of(repository.save(card.get()));
        } else return card;
    }

    public Optional<Card> delete(Long id) {
        Optional<Card> card = repository.findById(id);
        if (card.isPresent()) {
            repository.deleteById(id);
        }
        return card;
    }

    public boolean addPhoto(Long id, Photo photo, int position) {
        Optional<Card> card = repository.findById(id);
        if (card.isPresent()) {
            position = Math.min(position, card.get().getPhotos().size());
            if (card.get().addPhoto(photo, position)) {
                repository.save(card.get());
                return true;
            }
        }
        return false;
    }

    public boolean removePhoto(Long id, int position) {
        Optional<Card> card = repository.findById(id);
        if (card.isPresent()) {
            position = Math.min(position, card.get().getPhotos().size());
            if (card.get().removePhoto(position)) {
                repository.save(card.get());
                return true;
            }
        }
        return false;
    }

    public boolean movePhoto(Long id, int oldPosition, int newPosition) {
        Optional<Card> card = repository.findById(id);
        if (card.isPresent()) {
            oldPosition = Math.min(oldPosition, card.get().getPhotos().size());
            newPosition = Math.min(newPosition, card.get().getPhotos().size());
            Photo photoToMove = card.get().getPhoto(oldPosition);
            if (photoToMove != null
                    && card.get().removePhoto(oldPosition)
                    && card.get().addPhoto(photoToMove, newPosition)) {
                repository.save(card.get());
                return true;
            }
        }
        return false;
    }

}
