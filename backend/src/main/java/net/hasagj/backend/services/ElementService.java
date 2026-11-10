package net.hasagj.backend.services;


import net.hasagj.backend.dto.ElementDto;
import net.hasagj.backend.models.Element;
import net.hasagj.backend.models.ElementPlacement;
import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.repositories.ElementPlacementRepository;
import net.hasagj.backend.repositories.ElementRepository;
import net.hasagj.backend.util.Coordinates;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ElementService {
    private final ElementRepository repository;
    private final ElementPlacementRepository epRepository;

    private ElementService(ElementRepository repository, ElementPlacementRepository epRepository) {
        this.repository = repository;
        this.epRepository = epRepository;
    }

    public List<ElementDto> getAll(Long collectionId) {
        List<ElementDto> dtoList = new ArrayList<>();
        repository.findByCollectionId(collectionId).forEach(element -> dtoList.add(new ElementDto(element.getName(), element.getDescription(), element.getClass().getSimpleName(), element.getCollection().getId())));
        return dtoList;
    }

    public boolean updatePlacement(Long elementId, Shelf shelf, Coordinates coordinates) {
        Optional<Element> element = repository.findById(elementId);
        if (element.isPresent()) {
            ElementPlacement placement = element.get().getPlacement();
            Shelf oldShelf = placement.getShelf();
            placement.setCoordinates(coordinates);
            if (shelf != oldShelf && shelf != null) {
                oldShelf.removePlacement(oldShelf.getPlacements().indexOf(placement));
                shelf.addPlacement(placement);
            }
            epRepository.save(placement);
            return true;
        } else return false;
    }

    public boolean updatePlacement(Long elementId, Shelf shelf, Float x, Float y) {
        Optional<Element> element = repository.findById(elementId);
        if (element.isPresent()) {
            ElementPlacement placement = element.get().getPlacement();
            Shelf oldShelf = placement.getShelf();
            x = x == null ? placement.getCoordinates().x() : x;
            y = y == null ? placement.getCoordinates().y() : y;
            placement.setCoordinates(x, y);
            if (shelf != oldShelf && shelf != null) {
                oldShelf.removePlacement(oldShelf.getPlacements().indexOf(placement));
                shelf.addPlacement(placement);
            }
            epRepository.save(placement);
            return true;
        } else return false;
    }
}
