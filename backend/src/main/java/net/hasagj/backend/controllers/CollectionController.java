package net.hasagj.backend.controllers;

import net.hasagj.backend.dto.CollectionDto;
import net.hasagj.backend.models.Collection;
import net.hasagj.backend.services.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CollectionController {

    private final CollectionService service;

    public CollectionController(CollectionService service) {
        this.service = service;
    }

    @GetMapping("/collections")
    public ResponseEntity<List<Collection>> getCollections() {
        List<Collection> collections = service.getAll();
        return ResponseEntity.ok(collections);
    }

    @GetMapping("/collections/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable Long id) {
        Optional<Collection> collection = service.get(id);
        return collection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/collections")
    public ResponseEntity<Collection> createCollection(@RequestBody CollectionDto request) {
        Collection collection = service.create(request.name(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(collection);
    }

    @PatchMapping("/collections/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable Long id, @RequestBody CollectionDto request) {
        Optional<Collection> collection = service.update(id, request.name(), request.description());
        return collection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/collections/{id}")
    public ResponseEntity<Collection> deleteCollection(@PathVariable Long id) {
        Optional<Collection> collection = service.delete(id);
        return collection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
