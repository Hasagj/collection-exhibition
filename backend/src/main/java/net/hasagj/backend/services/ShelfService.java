package net.hasagj.backend.services;

import net.hasagj.backend.models.Collection;
import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.repositories.ShelfRepository;
import net.hasagj.backend.util.Coordinates;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelfService {
    private final ShelfRepository repository;

    public ShelfService(ShelfRepository repository) { this.repository = repository; }

    public Shelf create(String name, @Nullable String description, Collection collection, @Nullable Shelf shelf, @Nullable Coordinates coordinates) {
        name = name == null || name.isBlank() ? "Untitled" : name.trim();
        description = description == null || description.isBlank() ? null : description;
        Shelf newShelf = shelf == null && coordinates == null ?
                Shelf.createRoot(collection)
                : new Shelf(
                        name,
                        description,
                        collection,
                        shelf,
                        coordinates
                );
        return repository.save(newShelf);
    }

    public Shelf create(String name, @Nullable String description, Collection collection, @Nullable Shelf shelf, @Nullable Float x, @Nullable Float y) {
        name = name == null || name.isBlank() ? "Untitled" : name.trim();
        description = description == null || description.isBlank() ? null : description;
        Shelf newShelf = shelf == null && x == null && y == null ?
                Shelf.createRoot(collection)
                : new Shelf(
                name,
                description,
                collection,
                shelf,
                x == null ? 0F : x,
                y == null ? 0F : y
        );
        return repository.save(newShelf);
    }

    public List<Shelf> getAll() { return repository.findAll(); }

    public Optional<Shelf> get(Long id) { return repository.findById(id); }

    public Optional<Shelf> update(Long id, @Nullable String name, @Nullable String description) {
        Optional<Shelf> shelf = repository.findById(id);
        if (shelf.isPresent()) {
            name = name == null || name.isBlank() ? shelf.get().getName() : name;
            description = description == null ? shelf.get().getDescription() : description;
            shelf.get().setName(name);
            shelf.get().setDescription(description);
            return Optional.of(repository.save(shelf.get()));
        } else return shelf;
    }

    public Optional<Shelf> delete(Long id) {
        Optional<Shelf> shelf = repository.findById(id);
        if (shelf.isPresent()) {
            repository.deleteById(id);
        }
        return shelf;
    }
}
