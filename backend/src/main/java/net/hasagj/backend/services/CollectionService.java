package net.hasagj.backend.services;

import net.hasagj.backend.models.Collection;
import net.hasagj.backend.repositories.CollectionRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {
    private final CollectionRepository repository;

    public CollectionService(CollectionRepository repository) {
        this.repository = repository;
    }

    public Collection create(String name, @Nullable String description) {
        name = name == null || name.isBlank() ? "Untitled" : name.trim();
        Collection collection =
                new Collection(name.trim(), description == null || description.isBlank() ? null : description);
        return repository.save(collection);
    }

    public List<Collection> getAll() {
        return repository.findAll();
    }

    public Optional<Collection> get(Long id) {
        return repository.findById(id);
    }

    public Optional<Collection> update(Long id, @Nullable String name, @Nullable String description) {
        Optional<Collection> collection = repository.findById(id);
        if (collection.isPresent()) {
            name = name == null || name.isBlank() ? collection.get().getName() : name;
            description = description == null ? collection.get().getDescription() : description;
            collection.get().setName(name);
            collection.get().setDescription(description);
            return Optional.of(repository.save(collection.get()));
        } else return collection;
    }

    public Optional<Collection> delete(Long id) {
        Optional<Collection> collection = repository.findById(id);
        if (collection.isPresent()) {
            repository.deleteById(id);
        }
        return collection;
    }
}
