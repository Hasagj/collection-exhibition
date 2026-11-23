package net.hasagj.backend.services;

import net.hasagj.backend.dto.ElementResponse;
import net.hasagj.backend.dto.NoteDto;
import net.hasagj.backend.exceptions.collection.CollectionNotFoundException;
import net.hasagj.backend.exceptions.element.ElementNotFoundException;
import net.hasagj.backend.exceptions.note.InvalidNoteTargetException;
import net.hasagj.backend.exceptions.note.NoteNotFoundException;
import net.hasagj.backend.exceptions.shelf.ShelfNotFoundException;
import net.hasagj.backend.models.*;
import net.hasagj.backend.repositories.CollectionRepository;
import net.hasagj.backend.repositories.ElementRepository;
import net.hasagj.backend.repositories.NoteRepository;
import net.hasagj.backend.repositories.ShelfRepository;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NoteService {
    private final NoteRepository noteRepository;
    private final CollectionRepository collectionRepository;
    private final ShelfRepository shelfRepository;
    private final ElementRepository elementRepository;
    private final ElementPlacementService elementPlacementService;

    public NoteService(
            NoteRepository noteRepository,
            CollectionRepository collectionRepository,
            ShelfRepository shelfRepository,
            ElementRepository elementRepository,
            ElementPlacementService elementPlacementService
    ) {
        this.noteRepository = noteRepository;
        this.collectionRepository = collectionRepository;
        this.shelfRepository = shelfRepository;
        this.elementRepository = elementRepository;
        this.elementPlacementService = elementPlacementService;
    }

    @Transactional
    public NoteDto.Response create(Long collectionId, NoteDto.CreateRequest createRequest) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() ->
                new CollectionNotFoundException(collectionId));
        Shelf shelf = createRequest.shelfId() == null ?
                collection.getRootShelf()
                : shelfRepository.findByIdAndCollectionId(
                createRequest.shelfId(),
                collectionId
        ).orElseThrow(() ->
                new ShelfNotFoundException(createRequest.shelfId(), collectionId));
        Note note = new Note(
                createRequest.description(),
                collection,
                shelf,
                createRequest.coordinates()
        );
        return NoteDto.Response.from(noteRepository.save(note));
    }

    public List<NoteDto.SimpleResponse> getAll(Long collectionId) {
        return noteRepository.findAllByCollectionId(collectionId).stream().map(NoteDto.SimpleResponse::from).toList();
    }

    public NoteDto.Response get(Long id, Long collectionId) {
        return NoteDto.Response.from(noteRepository.findWithElementsByIdAndCollectionId(id, collectionId).orElseThrow(() ->
                new NoteNotFoundException(id, collectionId)));
    }

    @Transactional
    public NoteDto.Response update(Long id, Long collectionId, NoteDto.UpdateRequest updateRequest) {
        Shelf shelf = updateRequest.shelfId() != null ?
                shelfRepository.findByIdAndCollectionId(updateRequest.shelfId(), collectionId).orElseThrow(() ->
                new ShelfNotFoundException(updateRequest.shelfId(), collectionId))
                : null;
        Note note = this.find(id, collectionId);
        if (updateRequest.description() != null) {
            note.setDescription(updateRequest.description().isEmpty() ? null : updateRequest.description());
        }
        elementPlacementService.update(note, shelf, updateRequest.coordinates());
        return NoteDto.Response.from(note);
    }

    @Transactional
    public NoteDto.SimpleResponse delete(Long id, Long collectionId) {
        Note note = this.find(id, collectionId);
        noteRepository.delete(note);
        return NoteDto.SimpleResponse.from(note);
    }

    @Transactional
    public ElementResponse addElement(Long id, Long collectionId, Long elementId) {
        if (id.equals(elementId)) throw new InvalidNoteTargetException(id);
        Note note = find(id, collectionId);
        Element element = elementRepository.findByIdAndCollectionId(elementId, collectionId).orElseThrow(() ->
                new ElementNotFoundException(elementId, collectionId));
        if (element instanceof Note) throw new InvalidNoteTargetException(id, elementId);
        note.addElement(element);
        return new ElementResponse(
                elementId,
                element.getName(),
                element.getDescription(),
                element instanceof HibernateProxy proxy ?
                        proxy.getHibernateLazyInitializer().getPersistentClass().getSimpleName()
                        : element.getClass().getSimpleName(),
                collectionId);
    }

    @Transactional
    public ElementResponse removeElement(Long id, Long collectionId, Long elementId) {
        Note note = find(id, collectionId);
        Element element = note.getElements().stream().filter(elementNote ->
                elementNote.getId().equals(elementId)).findFirst().orElseThrow(() ->
                new ElementNotFoundException(elementId, collectionId));
        note.removeElement(element);
        return new ElementResponse(
                elementId,
                element.getName(),
                element.getDescription(),
                element instanceof HibernateProxy proxy ?
                        proxy.getHibernateLazyInitializer().getPersistentClass().getSimpleName()
                        : element.getClass().getSimpleName(),
                collectionId);
    }

    private Note find(Long id, Long collectionId) {
        return noteRepository.findByIdAndCollectionId(id, collectionId).orElseThrow(() ->
                new NoteNotFoundException(id, collectionId));
    }
}
