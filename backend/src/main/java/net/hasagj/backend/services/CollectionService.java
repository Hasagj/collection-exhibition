package net.hasagj.backend.services;

import net.hasagj.backend.dto.CollectionDto;
import net.hasagj.backend.exceptions.collection.CollectionNotFoundException;
import net.hasagj.backend.models.Collection;
import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.repositories.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CollectionService {
    private final CollectionRepository repository;

    public CollectionService(CollectionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CollectionDto.Response create(CollectionDto.CreateRequest createRequest) {
        Collection collection = new Collection(createRequest.name(), createRequest.description());
        Shelf rootShelf = Shelf.createRoot(collection);
        collection.setRootShelf(rootShelf);
        return CollectionDto.Response.from(repository.save(collection));
    }

    public List<CollectionDto.SimpleResponse> getAll() {
        return repository.findAllProjectedBy();
    }

    public CollectionDto.Response get(Long id) {
        return CollectionDto.Response.from(this.find(id));
    }

    @Transactional
    public CollectionDto.Response update(Long id, CollectionDto.UpdateRequest updateRequest) {
        Collection collection = this.find(id);
        if (updateRequest.name() != null) collection.setName(updateRequest.name());
        if (updateRequest.description() != null) {
            collection.setDescription(updateRequest.description().isEmpty() ? null : updateRequest.description());
        }
        return CollectionDto.Response.from(collection);
    }

    @Transactional
    public CollectionDto.SimpleResponse delete(Long id) {
        Collection collection = this.find(id);
        repository.delete(collection);
        return CollectionDto.SimpleResponse.from(collection);
    }

    private Collection find(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new CollectionNotFoundException(id));
    }
}
