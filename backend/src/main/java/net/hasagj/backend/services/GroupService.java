package net.hasagj.backend.services;

import net.hasagj.backend.dto.CardDto;
import net.hasagj.backend.dto.GroupDto;
import net.hasagj.backend.dto.PhotoDto;
import net.hasagj.backend.exceptions.card.CardBadPositionException;
import net.hasagj.backend.exceptions.card.CardNotFoundException;
import net.hasagj.backend.exceptions.collection.CollectionNotFoundException;
import net.hasagj.backend.exceptions.group.GroupNotFoundException;
import net.hasagj.backend.exceptions.photo.PhotoBadPathException;
import net.hasagj.backend.exceptions.photo.PhotoNotFoundException;
import net.hasagj.backend.exceptions.shelf.ShelfNotFoundException;
import net.hasagj.backend.models.*;
import net.hasagj.backend.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GroupService {
    private final GroupRepository groupRepository;
    private final CollectionRepository collectionRepository;
    private final ShelfRepository shelfRepository;
    private final CardRepository cardRepository;
    private final PhotoRepository photoRepository;
    private final ElementPlacementService elementPlacementService;

    public GroupService(
            GroupRepository groupRepository,
            CollectionRepository collectionRepository,
            ShelfRepository shelfRepository,
            CardRepository cardRepository,
            PhotoRepository photoRepository,
            ElementPlacementService elementPlacementService
    ) {
        this.groupRepository = groupRepository;
        this.collectionRepository = collectionRepository;
        this.shelfRepository = shelfRepository;
        this.cardRepository = cardRepository;
        this.photoRepository = photoRepository;
        this.elementPlacementService = elementPlacementService;
    }

    @Transactional
    public GroupDto.Response create(Long collectionId, GroupDto.CreateRequest createRequest) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() ->
                new CollectionNotFoundException(collectionId));
        Shelf shelf = createRequest.shelfId() == null ?
                collection.getRootShelf()
                : shelfRepository.findByIdAndCollectionId(
                createRequest.shelfId(),
                collectionId
        ).orElseThrow(() -> new ShelfNotFoundException(createRequest.shelfId(), collectionId));
        Group group = new Group(
                createRequest.name(),
                createRequest.description(),
                collection,
                shelf,
                createRequest.coordinates()
        );
        return GroupDto.Response.from(groupRepository.save(group));
    }

    public List<GroupDto.SimpleResponse> getAll(Long collectionId) {
        return groupRepository.findAllByCollectionId(collectionId).stream().map(GroupDto.SimpleResponse::from).toList();
    }

    public GroupDto.Response get(Long id, Long collectionId) {
        return GroupDto.Response.from(groupRepository.findWithCardsByIdAndCollectionId(id, collectionId).orElseThrow(() ->
                new GroupNotFoundException(id, collectionId)));
    }

    @Transactional
    public GroupDto.Response update(Long id, Long collectionId, GroupDto.UpdateRequest updateRequest) {
        Shelf shelf = updateRequest.shelfId() != null ?
                shelfRepository.findByIdAndCollectionId(updateRequest.shelfId(), collectionId).orElseThrow(() ->
                        new ShelfNotFoundException(updateRequest.shelfId(), collectionId))
                : null;
        Group group = this.find(id, collectionId);
        if (updateRequest.name() != null) group.setName(updateRequest.name());
        if (updateRequest.description() != null) {
            group.setDescription(updateRequest.description().isEmpty() ? null : updateRequest.description());
        }
        elementPlacementService.update(group, shelf, updateRequest.coordinates());
        return GroupDto.Response.from(group);
    }

    @Transactional
    public GroupDto.SimpleResponse delete(Long id, Long collectionId) {
        Group group = this.find(id, collectionId);
        groupRepository.delete(group);
        return GroupDto.SimpleResponse.from(group);
    }

    @Transactional
    public PhotoDto.Response addPhoto(Long id, Long collectionId, String path) {
        Group group = this.find(id, collectionId);
        Collection collection = group.getCollection();
        if (path == null) throw new PhotoBadPathException(path);
        Photo photo =
                photoRepository.findByPathAndCollectionId(path, collectionId).orElse(new Photo(path, collection));
        group.setPhoto(photo);
        return PhotoDto.Response.from(photo);
    }

    @Transactional
    public PhotoDto.Response removePhoto(Long id, Long collectionId) {
        Group group = groupRepository.findByIdAndCollectionId(id, collectionId).orElseThrow(() ->
                new GroupNotFoundException(id, collectionId));
        if (group.getPhoto() == null) throw new PhotoNotFoundException(id);
        PhotoDto.Response response = PhotoDto.Response.from(group.getPhoto());
        group.setPhoto(null);
        return response;
    }

    @Transactional
    public CardDto.SimpleResponse addCard(Long id, Long collectionId, Long cardId, Integer position) {
        Group group = this.find(id, collectionId);
        Card card = cardRepository.findByIdAndCollectionId(cardId, collectionId).orElseThrow(() ->
                new CardNotFoundException(cardId, collectionId));
        position = Math.min(position, group.getCards().size());
        if (!group.addCard(card, position)) throw new CardBadPositionException(position);
        return CardDto.SimpleResponse.from(card);
    }

    @Transactional
    public CardDto.SimpleResponse removeCard(Long id, Long collectionId, Long cardId) {
        Group group = this.find(id, collectionId);
        Card card = group.getCards().stream().filter(cardGroup ->
                cardGroup.getId().equals(cardId)).findFirst().orElseThrow(() ->
                new CardNotFoundException(cardId, collectionId));
        if (!group.removeCard(card)) throw new CardNotFoundException(cardId, collectionId, id);
        return CardDto.SimpleResponse.from(card);
    }

    @Transactional
    public CardDto.SimpleResponse moveCard(Long id, Long collectionId, Long cardId, Integer position) {
        if (position == null || position < 0) throw new CardBadPositionException(position);
        Group group = this.find(id, collectionId);
        Card card = group.getCards().stream().filter(cardGroup ->
                cardGroup.getId().equals(cardId)).findFirst().orElseThrow(() ->
                new CardNotFoundException(cardId, collectionId));
        position = Math.min(position, group.getCards().size());
        group.removeCard(card);
        if (!group.addCard(card, position)) throw new CardBadPositionException(position);
        return CardDto.SimpleResponse.from(card);
    }

    private Group find(Long id, Long collectionId) {
        return groupRepository.findByIdAndCollectionId(id, collectionId).orElseThrow(() ->
                new GroupNotFoundException(id, collectionId));
    }
}
