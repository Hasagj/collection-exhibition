package net.hasagj.backend.controllers;

import jakarta.validation.Valid;
import net.hasagj.backend.dto.CardDto;
import net.hasagj.backend.dto.PhotoDto;
import net.hasagj.backend.services.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections/{collectionId}/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<CardDto.SimpleResponse> getCards(@PathVariable Long collectionId) {
        return cardService.getAll(collectionId);
    }

    @GetMapping("/{id}")
    public CardDto.Response getCard(@PathVariable Long id, @PathVariable Long collectionId) {
        return cardService.get(id, collectionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto.Response createCard(
            @PathVariable Long collectionId,
            @Valid @RequestBody CardDto.CreateRequest createRequest
    ) {
        return cardService.create(collectionId, createRequest);
    }

    @PatchMapping("/{id}")
    public CardDto.Response updateCard(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @Valid @RequestBody CardDto.UpdateRequest updateRequest
    ) {
        return cardService.update(id, collectionId, updateRequest);
    }

    @DeleteMapping("/{id}")
    public CardDto.SimpleResponse deleteCard(@PathVariable Long id, @PathVariable Long collectionId) {
        return cardService.delete(id, collectionId);
    }

    @PostMapping("/{id}/photos")
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoDto.Response addPhotoToCard(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @RequestParam String path,
            @RequestParam(required = false, defaultValue = "0") Integer position
    ) {
        return cardService.addPhoto(id, collectionId, path, position);
    }

    @PutMapping("/{id}/photos")
    public PhotoDto.Response movePhotoInsideCard(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @RequestParam String path,
            @RequestParam(required = false, defaultValue = "0") Integer position
    ) {
        return cardService.movePhoto(id, collectionId, path, position);
    }

    @DeleteMapping("/{id}/photos")
    public PhotoDto.SimpleResponse removePhotoFromCard(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @RequestParam String path
    ) {
        return cardService.removePhoto(id, collectionId, path);
    }


}
