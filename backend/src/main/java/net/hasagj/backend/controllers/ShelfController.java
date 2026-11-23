package net.hasagj.backend.controllers;

import jakarta.validation.Valid;
import net.hasagj.backend.dto.ShelfDto;
import net.hasagj.backend.services.ShelfService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections/{collectionId}/shelves")
public class ShelfController {

    private final ShelfService shelfService;


    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @GetMapping
    public List<ShelfDto.SimpleResponse> getShelves(@PathVariable Long collectionId) {
        return shelfService.getAll(collectionId);
    }

    @GetMapping("/{id}")
    public ShelfDto.Response getShelf(@PathVariable Long id, @PathVariable Long collectionId) {
        return shelfService.get(id, collectionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShelfDto.Response createShelf(
            @PathVariable Long collectionId,
            @Valid @RequestBody ShelfDto.CreateRequest createRequest
    ) {
        return shelfService.create(collectionId, createRequest);
    }

    @PatchMapping("/{id}")
    public ShelfDto.Response updateShelf(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @Valid @RequestBody ShelfDto.UpdateRequest updateRequest
    ) {
        return shelfService.update(id, collectionId, updateRequest);
    }

    @DeleteMapping("/{id}")
    public ShelfDto.SimpleResponse deleteShelf(@PathVariable Long id, @PathVariable Long collectionId) {
        return shelfService.delete(id, collectionId);
    }
}
