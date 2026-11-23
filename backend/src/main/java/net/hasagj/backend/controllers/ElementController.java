package net.hasagj.backend.controllers;

import net.hasagj.backend.dto.ElementResponse;
import net.hasagj.backend.services.ElementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/collection/{collectionId}/elements")
public class ElementController {
    private final ElementService elementService;

    public ElementController(ElementService elementService) {
        this.elementService = elementService;
    }

    @GetMapping
    public List<ElementResponse> getElements(@PathVariable Long collectionId) {
        return elementService.getAll(collectionId);
    }
}
