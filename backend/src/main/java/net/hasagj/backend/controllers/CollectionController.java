package net.hasagj.backend.controllers;

import net.hasagj.backend.dto.CollectionDto;
import net.hasagj.backend.models.Collection;
import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.services.CollectionService;
import net.hasagj.backend.services.ShelfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CollectionController {

    private final CollectionService collectionService;
    private final ShelfService shelfService;

    public CollectionController(CollectionService collectionService, ShelfService shelfService) {
        this.collectionService = collectionService;
        this.shelfService = shelfService;
    }

    @GetMapping("/collections")
    public ResponseEntity<List<Collection>> getCollections() {
        List<Collection> collections = collectionService.getAll();
        return ResponseEntity.ok(collections);
    }

    @GetMapping("/collections/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable Long id) {
        Optional<Collection> collection = collectionService.get(id);
        return collection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/collections")
    public ResponseEntity<Collection> createCollection(@RequestBody CollectionDto request) {
        Collection collection = collectionService.create(request.name(), request.description());
        Shelf root = shelfService.create(null, null, collection, null, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(collection);
    }

    @PatchMapping("/collections/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable Long id, @RequestBody CollectionDto request) {
        Optional<Collection> collection = collectionService.update(id, request.name(), request.description());
        return collection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/collections/{id}")
    public ResponseEntity<Collection> deleteCollection(@PathVariable Long id) {
        Optional<Collection> collection = collectionService.delete(id);
        return collection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
