package net.hasagj.backend.services;


import net.hasagj.backend.dto.ElementResponse;
import net.hasagj.backend.repositories.ElementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class ElementService {
    private final ElementRepository repository;

    public ElementService(ElementRepository repository) {
        this.repository = repository;
    }

    public List<ElementResponse> getAll(Long collectionId) {
        return repository.findAllByCollectionId(collectionId).stream().map(ElementResponse::from).toList();
    }
}
