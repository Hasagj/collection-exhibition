package net.hasagj.backend.services;

import net.hasagj.backend.models.Photo;
import net.hasagj.backend.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    private final PhotoRepository repository;

    public PhotoService(PhotoRepository repository) {
        this.repository = repository;
    }

    public Photo create(Photo photo) {
        return repository.save(photo);
    }
}
