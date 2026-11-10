package net.hasagj.backend.services;

import net.hasagj.backend.models.Photo;
import net.hasagj.backend.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    private final PhotoRepository repository;

    public PhotoService(PhotoRepository repository) { this.repository = repository; }

    public Photo create(String path) {
        Photo photo = new Photo(path);
        return repository.save(photo);
    }

    public List<Photo> getAll() { return repository.findAll(); }

    public Optional<Photo> get(Long id) { return repository.findById(id); }

    public Optional<Photo> update(Long id, String path) {
        Optional<Photo> photo = repository.findById(id);
        if (photo.isPresent()) {
            photo.get().setPath(path);
            return Optional.of(repository.save(photo.get()));
        } else return photo;
    }

    public Optional<Photo> delete(Long id) {
        Optional<Photo> photo = repository.findById(id);
        if (photo.isPresent()) {
            repository.deleteById(id);
        }
        return photo;
    }
}
