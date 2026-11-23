package net.hasagj.backend.services;

import net.hasagj.backend.dto.ShelfDto;
import net.hasagj.backend.exceptions.collection.CollectionNotFoundException;
import net.hasagj.backend.exceptions.shelf.RootShelfDeletionException;
import net.hasagj.backend.exceptions.shelf.RootShelfUpdateException;
import net.hasagj.backend.exceptions.shelf.ShelfNotFoundException;
import net.hasagj.backend.models.Collection;
import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.repositories.CollectionRepository;
import net.hasagj.backend.repositories.ShelfRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ShelfService {

    private final ElementPlacementService elementPlacementService;
    private final CollectionRepository collectionRepository;
    private final ShelfRepository shelfRepository;

    public ShelfService(
            ElementPlacementService elementPlacementService,
            CollectionRepository collectionRepository,
            ShelfRepository shelfRepository
    ) {
        this.elementPlacementService = elementPlacementService;
        this.collectionRepository = collectionRepository;
        this.shelfRepository = shelfRepository;
    }

    @Transactional
    public ShelfDto.Response create(Long collectionId, ShelfDto.CreateRequest createRequest) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(
                () -> new CollectionNotFoundException(collectionId));
        Shelf parentShelf = createRequest.shelfId() == null ?
               collection.getRootShelf() : this.find(createRequest.shelfId(), collectionId);
        Shelf shelf = new Shelf(
                createRequest.name(),
                createRequest.description(),
                collection,
                parentShelf,
                createRequest.coordinates()
        );
        return ShelfDto.Response.from(shelfRepository.save(shelf));
    }

    public List<ShelfDto.SimpleResponse> getAll(Long collectionId) {
        return shelfRepository.findAllByCollectionId(collectionId).stream().map(ShelfDto.SimpleResponse::from).toList();
    }

    public ShelfDto.Response get(Long id, Long collectionId) {
        return ShelfDto.Response.from(
                shelfRepository.findWithInnerPlacementsByIdAndCollectionId(id, collectionId).orElseThrow(() ->
                        new ShelfNotFoundException(id, collectionId)));
    }

    @Transactional
    public ShelfDto.Response update(Long id, Long collectionId, ShelfDto.UpdateRequest updateRequest) {
        Shelf shelf = this.find(id, collectionId);
        if (shelf.isRoot()) throw new RootShelfUpdateException(id, collectionId);
        Shelf parentShelf = updateRequest.shelfId() != null ?
                this.find(updateRequest.shelfId(), collectionId)
                : null;
        if (updateRequest.name() != null) shelf.setName(updateRequest.name());
        if (updateRequest.description() != null) {
            shelf.setDescription(updateRequest.description().isEmpty() ? null : updateRequest.description());
        }
        elementPlacementService.update(shelf, parentShelf, updateRequest.coordinates());
        return ShelfDto.Response.from(shelf);
    }

    @Transactional
    public ShelfDto.SimpleResponse delete(Long id, Long collectionId) {
        Shelf shelf = this.find(id, collectionId);
        if (shelf.isRoot()) throw new RootShelfDeletionException(id, collectionId);
        shelfRepository.delete(shelf);
        return ShelfDto.SimpleResponse.from(shelf);
    }

    private Shelf find(Long id, Long collectionId) {
        return shelfRepository.findByIdAndCollectionId(id, collectionId).orElseThrow(() ->
                new ShelfNotFoundException(id, collectionId));
    }
}
