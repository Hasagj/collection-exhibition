package net.hasagj.backend.services;

import net.hasagj.backend.dto.PhotoDto;
import net.hasagj.backend.exceptions.collection.CollectionNotFoundException;
import net.hasagj.backend.exceptions.photo.PhotoNotFoundException;
import net.hasagj.backend.models.Collection;
import net.hasagj.backend.models.Photo;
import net.hasagj.backend.repositories.CollectionRepository;
import net.hasagj.backend.repositories.PhotoRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final CollectionRepository collectionRepository;

    public PhotoService(
            PhotoRepository photoRepository,
            CollectionRepository collectionRepository
    ) {
        this.photoRepository = photoRepository;
        this.collectionRepository = collectionRepository;
    }

    @Transactional
    public PhotoDto.SimpleResponse create(String path, Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() ->
                new CollectionNotFoundException(collectionId));
        Photo photo = new Photo(path, collection);
        return PhotoDto.SimpleResponse.from(photoRepository.save(photo));
    }

    public List<PhotoDto.SimpleResponse> getAll(Long collectionId) {
        return photoRepository.findAllByCollectionId(collectionId);
    }

    public PhotoDto.Response get(String path, Long collectionId) {
        return PhotoDto.Response.from(this.find(path, collectionId));
    }

    @Transactional
    public PhotoDto.SimpleResponse update(String oldPath, Long collectionId, @Nullable String newPath) {
        Photo photo = find(oldPath, collectionId);
        if (newPath != null) photo.setPath(newPath);
        return PhotoDto.SimpleResponse.from(photo);
    }

    @Transactional
    public PhotoDto.SimpleResponse delete(String path, Long collectionId) {
        Photo photo = this.find(path, collectionId);
        photoRepository.delete(photo);
        return PhotoDto.SimpleResponse.from(photo);
    }

    private Photo find(String path, Long collectionId) {
        return photoRepository.findByPathAndCollectionId(path, collectionId).orElseThrow(() ->
                new PhotoNotFoundException(path));
    }
}
