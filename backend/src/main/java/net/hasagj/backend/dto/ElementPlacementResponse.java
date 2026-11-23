package net.hasagj.backend.dto;

import net.hasagj.backend.models.ElementPlacement;
import net.hasagj.backend.util.Coordinates;
import org.jspecify.annotations.Nullable;

public record ElementPlacementResponse(
        Long id,
        Long shelfId,
        Long elementId,
        Coordinates coordinates
) {
    public static @Nullable ElementPlacementResponse from(@Nullable ElementPlacement elementPlacement) {
        return elementPlacement != null ?
                new ElementPlacementResponse(
                        elementPlacement.getId(),
                        elementPlacement.getShelf().getId(),
                        elementPlacement.getElement().getId(),
                        elementPlacement.getCoordinates())
                : null;
    }
}
