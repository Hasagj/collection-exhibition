package net.hasagj.backend.services;

import net.hasagj.backend.dto.CardDto;
import net.hasagj.backend.dto.PhotoDto;
import net.hasagj.backend.exceptions.card.CardNotFoundException;
import net.hasagj.backend.exceptions.photo.PhotoBadPathException;
import net.hasagj.backend.exceptions.photo.PhotoBadPositionException;
import net.hasagj.backend.exceptions.collection.CollectionNotFoundException;
import net.hasagj.backend.exceptions.photo.PhotoNotFoundException;
import net.hasagj.backend.exceptions.shelf.ShelfNotFoundException;
import net.hasagj.backend.models.Card;
import net.hasagj.backend.models.Collection;
import net.hasagj.backend.models.Photo;
import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;
    private final ShelfRepository shelfRepository;
    private final CollectionRepository collectionRepository;
    private final PhotoRepository photoRepository;
    private final ElementPlacementService elementPlacementService;

    public CardService(
            CardRepository cardRepository,
            ShelfRepository shelfRepository,
            CollectionRepository collectionRepository,
            PhotoRepository photoRepository,
            ElementPlacementService elementPlacementService
    ) {
        this.cardRepository = cardRepository;
        this.shelfRepository = shelfRepository;
        this.collectionRepository = collectionRepository;
        this.photoRepository = photoRepository;
        this.elementPlacementService = elementPlacementService;
    }

    @Transactional
    public CardDto.Response create(Long collectionId, CardDto.CreateRequest createRequest) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() ->
                new CollectionNotFoundException(collectionId));
        Shelf shelf = createRequest.shelfId() == null ?
                collection.getRootShelf()
                : shelfRepository.findByIdAndCollectionId(
                createRequest.shelfId(),
                collectionId
        ).orElseThrow(() ->
                new ShelfNotFoundException(createRequest.shelfId(), collectionId));
        Card card = new Card(
                createRequest.name(),
                createRequest.description(),
                collection,
                shelf,
                createRequest.coordinates()
        );
        return CardDto.Response.from(cardRepository.save(card));
    }

    public List<CardDto.SimpleResponse> getAll(Long collectionId) {
        return cardRepository.findAllByCollectionId(collectionId).stream().map(CardDto.SimpleResponse::from).toList();
    }

    public CardDto.Response get(Long id, Long collectionId) {
        return CardDto.Response.from(
                cardRepository.findWithPhotosByIdAndCollectionId(id, collectionId).orElseThrow(() ->
                        new CardNotFoundException(id, collectionId))
        );
    }

    @Transactional
    public CardDto.Response update(Long id, Long collectionId, CardDto.UpdateRequest updateRequest) {
        Shelf shelf = updateRequest.shelfId() != null ?
                shelfRepository.findByIdAndCollectionId(updateRequest.shelfId(), collectionId).orElseThrow(() ->
                        new ShelfNotFoundException(updateRequest.shelfId(), collectionId))
                : null;
        Card card = this.find(id, collectionId);
        if (updateRequest.name() != null) card.setName(updateRequest.name());
        if (updateRequest.description() != null) {
            card.setDescription(updateRequest.description().isEmpty() ? null : updateRequest.description());
        }
        elementPlacementService.update(card, shelf, updateRequest.coordinates());
        return CardDto.Response.from(card);
    }

    @Transactional
    public CardDto.SimpleResponse delete(Long id, Long collectionId) {
        Card card = this.find(id, collectionId);
        cardRepository.delete(card);
        return CardDto.SimpleResponse.from(card);
    }

    @Transactional
    public PhotoDto.Response addPhoto(Long id, Long collectionId, String path, Integer position) {
        if (path == null) throw new PhotoBadPathException(path);
        if (position == null || position < 0) throw new PhotoBadPositionException(position);
        Card card = this.find(id, collectionId);
        Collection collection = card.getCollection();
        Photo photo =
                photoRepository.findByPathAndCollectionId(path, collectionId).orElse(new Photo(path, collection));
        position = Math.min(position, card.getPhotos().size());
        if (!card.addPhoto(photo, position)) throw new PhotoBadPositionException(position);
        return PhotoDto.Response.from(photo);
    }

    @Transactional
    public PhotoDto.SimpleResponse removePhoto(Long id, Long collectionId, String path) {
        if (path == null) throw new PhotoBadPathException(path);
        Card card = this.find(id, collectionId);
        Photo photo = card.getPhotos().stream().filter(cardPhoto ->
                cardPhoto.getPath().equals(path)).findFirst().orElseThrow(() ->
                new PhotoNotFoundException(path, id));
        card.removePhoto(photo);
        return PhotoDto.SimpleResponse.from(photo);
    }

    @Transactional
    public PhotoDto.Response movePhoto(Long id, Long collectionId, String path, Integer position) {
        if (path == null) throw new PhotoBadPathException(path);
        if (position == null || position < 0) throw new PhotoBadPositionException(position);
        Card card = this.find(id, collectionId);
        Photo photo = card.getPhotos().stream().filter(cardPhoto ->
                cardPhoto.getPath().equals(path)).findFirst().orElseThrow(() ->
                new PhotoNotFoundException(path, id));
        position = Math.min(position, card.getPhotos().size());
        card.removePhoto(photo);
        if (!card.addPhoto(photo, position)) throw new PhotoBadPositionException(position);
        return PhotoDto.Response.from(photo);
    }

    private Card find(Long id, Long collectionId) {
        return cardRepository.findByIdAndCollectionId(id, collectionId).orElseThrow(() ->
                new CardNotFoundException(id, collectionId));
    }

}
