package net.hasagj.backend.services;

import net.hasagj.backend.models.Collection;
import net.hasagj.backend.repositories.CollectionRepository;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {
    private final CollectionRepository repository;

    public CollectionService(CollectionRepository repository) {
        this.repository = repository;
    }

    public Collection create(Collection collection) {
        return repository.save(collection);
    }
}
