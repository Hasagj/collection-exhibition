package net.hasagj.backend.services;

import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.repositories.ShelfRepository;
import org.springframework.stereotype.Service;

@Service
public class ShelfService {
    private final ShelfRepository repository;

    public ShelfService(ShelfRepository repository) {
        this.repository = repository;
    }

    public Shelf create(Shelf shelf) {
        return repository.save(shelf);
    }
}
