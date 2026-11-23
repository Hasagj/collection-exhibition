package net.hasagj.backend.services;


import net.hasagj.backend.dto.ElementResponse;
import net.hasagj.backend.repositories.ElementRepository;
import org.hibernate.proxy.HibernateProxy;
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
        return repository.findAllByCollectionId(collectionId).stream().map(element ->
                new ElementResponse(
                        element.getId(),
                        element.getName(),
                        element.getDescription(),
                        element instanceof HibernateProxy proxy ?
                                proxy.getHibernateLazyInitializer().getPersistentClass().getSimpleName()
                                : element.getClass().getSimpleName(),
                        collectionId)).toList();
    }
}
