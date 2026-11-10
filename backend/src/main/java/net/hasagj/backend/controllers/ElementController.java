package net.hasagj.backend.controllers;

import net.hasagj.backend.dto.ElementDto;
import net.hasagj.backend.models.Collection;
import net.hasagj.backend.models.Element;
import net.hasagj.backend.services.ElementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/collection")
public class ElementController {
    private final ElementService service;

    private ElementController(ElementService service) {
        this.service = service;
    }

    @GetMapping("/{collectionId}/elements")
    public ResponseEntity<List<ElementDto>> getElements(@PathVariable Long collectionId) {
        List<ElementDto> elements = service.getAll(collectionId);
        return ResponseEntity.ok(elements);
    }
}
