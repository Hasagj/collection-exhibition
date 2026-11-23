package net.hasagj.backend.dto;

import jakarta.validation.constraints.Size;
import net.hasagj.backend.models.Shelf;
import net.hasagj.backend.util.Coordinates;
import org.jspecify.annotations.Nullable;

import java.util.List;

public sealed interface ShelfDto {
    record CreateRequest(
            @Nullable @Size(max = 100) String name,
            @Nullable @Size(max = 5_000) String description,
            @Nullable Long shelfId,
            @Nullable Coordinates coordinates
    ) implements ShelfDto {
        public CreateRequest {
            name = name == null || name.isBlank() ? "Untitled" : name.trim();
            description = description == null || description.isBlank() ? null : description;
            coordinates = coordinates == null ? Coordinates.zero() : coordinates;
        }
    }

    record UpdateRequest(
            @Nullable @Size(max = 100) String name,
            @Nullable @Size(max = 5_000) String description,
            @Nullable Long shelfId,
            @Nullable Coordinates coordinates
    ) implements ShelfDto {
        public UpdateRequest {
            name = name == null || name.isBlank() ? null : name.trim();
            if (description != null) {
                description = description.isBlank() ? "" : description.trim();
            }
        }
    }

    record Response(
            Long id,
            String name,
            @Nullable String description,
            Long collectionId,
            ElementPlacementResponse placement,
            List<ElementPlacementResponse> innerPlacements
    ) implements ShelfDto {
        public static Response from(Shelf shelf) {
            return new Response(
                    shelf.getId(),
                    shelf.getName(),
                    shelf.getDescription(),
                    shelf.getCollection().getId(),
                    ElementPlacementResponse.from(shelf.getPlacement()),
                    shelf.getInnerPlacements().stream().map(ElementPlacementResponse::from).toList()
            );
        }
    }

    record SimpleResponse(
            Long id,
            String name,
            Long collectionId,
            ElementPlacementResponse placement
    ) implements ShelfDto {
        public static SimpleResponse from(Shelf shelf) {
            return new SimpleResponse(
                    shelf.getId(),
                    shelf.getName(),
                    shelf.getCollection().getId(),
                    ElementPlacementResponse.from(shelf.getPlacement()));
        }
    }
}
