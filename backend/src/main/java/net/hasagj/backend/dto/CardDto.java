package net.hasagj.backend.dto;

import jakarta.validation.constraints.Size;
import net.hasagj.backend.models.Card;
import net.hasagj.backend.util.Coordinates;
import org.jspecify.annotations.Nullable;

import java.util.List;

public sealed interface CardDto {
    record CreateRequest(
            @Nullable @Size(max = 100) String name,
            @Nullable @Size(max = 1_000) String description,
            @Nullable Long shelfId,
            @Nullable Coordinates coordinates
    ) implements CardDto {
        public CreateRequest {
            name = name == null || name.isBlank() ? "Untitled" : name.trim();
            description = description == null || description.isBlank() ? null : description;
            coordinates = coordinates == null ? Coordinates.zero() : coordinates;
        }
    }
    record UpdateRequest(
            @Nullable @Size(max = 100) String name,
            @Nullable @Size(max = 1_000) String description,
            @Nullable Long shelfId,
            @Nullable Coordinates coordinates
    ) implements CardDto {
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
            List<PhotoDto.SimpleResponse> photos
    ) implements CardDto {
        public static Response from(Card card) {
            return new Response(
                    card.getId(),
                    card.getName(),
                    card.getDescription(),
                    card.getCollection().getId(),
                    ElementPlacementResponse.from(card.getPlacement()),
                    card.getPhotos().stream().map(PhotoDto.SimpleResponse::from).toList()
            );
        }
    }
    record SimpleResponse(
            Long id,
            String name,
            ElementPlacementResponse placement
    ) implements CardDto {
        public static SimpleResponse from(Card card) {
            return new SimpleResponse(
                    card.getId(),
                    card.getName(),
                    ElementPlacementResponse.from(card.getPlacement())
            );
        }
    }
}
