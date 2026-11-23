package net.hasagj.backend.services;

import net.hasagj.backend.exceptions.placement.BadElementMovingException;
import net.hasagj.backend.models.Element;
import net.hasagj.backend.models.ElementPlacement;
import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.util.Coordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ElementPlacementService {

    public ElementPlacementService() { }

    @Transactional
    public ElementPlacement update(Element element, Shelf newShelf, Coordinates coordinates) {
        ElementPlacement placement = element.getPlacement();
        Shelf oldShelf = placement.getShelf();
        if (coordinates != null) placement.setCoordinates(coordinates);
        if (newShelf != null) {
            if (newShelf.getId().equals(oldShelf.getId()) ||
                            element instanceof Shelf shelf
                                    && !newShelf.isRoot()
                                    && (newShelf.getPlacement().getShelf().getId().equals(shelf.getId()) ||
                                    newShelf.getId().equals(shelf.getId()))) {
                throw new BadElementMovingException(element.getId(), newShelf.getId());
            }
            oldShelf.removePlacement(oldShelf.getInnerPlacements().indexOf(placement));
            newShelf.addPlacement(placement);
        }
        return placement;
    }
}
