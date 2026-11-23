package net.hasagj.backend.controllers;

import jakarta.validation.Valid;
import net.hasagj.backend.dto.ElementResponse;
import net.hasagj.backend.dto.NoteDto;
import net.hasagj.backend.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections/{collectionId}/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteDto.SimpleResponse> getNotes(@PathVariable Long collectionId) {
        return noteService.getAll(collectionId);
    }

    @GetMapping("/{id}")
    public NoteDto.Response getNote(@PathVariable Long id, @PathVariable Long collectionId) {
        return noteService.get(id, collectionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDto.Response createNote(
            @PathVariable Long collectionId,
            @Valid @RequestBody NoteDto.CreateRequest createRequest
    ) {
        return noteService.create(collectionId, createRequest);
    }

    @PatchMapping("/{id}")
    public NoteDto.Response updateNote(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @Valid @RequestBody NoteDto.UpdateRequest updateRequest
    ) {
        return noteService.update(id, collectionId, updateRequest);
    }

    @DeleteMapping("/{id}")
    public NoteDto.SimpleResponse deleteNote(@PathVariable Long id, @PathVariable Long collectionId) {
        return noteService.delete(id, collectionId);
    }

    @PostMapping("/{id}/elements/{elementId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ElementResponse addElementToNote(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @PathVariable Long elementId
    ) {
        return noteService.addElement(id, collectionId, elementId);
    }

    @DeleteMapping("/{id}/elements/{elementId}")
    public ElementResponse removeElementFromNote(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @PathVariable Long elementId
    ) {
        return noteService.removeElement(id, collectionId, elementId);
    }
}
