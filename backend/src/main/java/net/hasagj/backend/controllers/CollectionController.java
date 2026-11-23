package net.hasagj.backend.controllers;

import jakarta.validation.Valid;
import net.hasagj.backend.dto.CollectionDto;
import net.hasagj.backend.services.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public List<CollectionDto.SimpleResponse> getCollections() {
        return collectionService.getAll();
    }

    @GetMapping("/{id}")
    public CollectionDto.Response getCollectionById(@PathVariable Long id) {
        return collectionService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionDto.Response createCollection(@Valid @RequestBody CollectionDto.CreateRequest createRequest) {
        return collectionService.create(createRequest);
    }

    @PatchMapping("/{id}")
    public CollectionDto.Response updateCollection(
            @PathVariable Long id, @Valid @RequestBody CollectionDto.UpdateRequest updateRequest) {
        return collectionService.update(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    public CollectionDto.SimpleResponse deleteCollection(@PathVariable Long id) {
        return collectionService.delete(id);
    }
}
