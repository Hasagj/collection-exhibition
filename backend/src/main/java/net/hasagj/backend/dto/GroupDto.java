package net.hasagj.backend.dto;

import jakarta.validation.constraints.Size;
import net.hasagj.backend.models.Group;
import net.hasagj.backend.util.Coordinates;
import org.jspecify.annotations.Nullable;

import java.util.List;

public sealed interface GroupDto {
    record CreateRequest(
            @Nullable @Size(max = 100) String name,
            @Nullable @Size(max = 5_000) String description,
            @Nullable Long shelfId,
            @Nullable Coordinates coordinates
    ) implements GroupDto {
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
    ) implements GroupDto {
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
            List<CardDto.SimpleResponse> cards
    ) implements GroupDto {
        public static Response from(Group group) {
            return new Response(
                    group.getId(),
                    group.getName(),
                    group.getDescription(),
                    group.getCollection().getId(),
                    ElementPlacementResponse.from(group.getPlacement()),
                    group.getCards().stream().map(CardDto.SimpleResponse::from).toList()
            );
        }
    }

    record SimpleResponse(
            Long id,
            String name,
            ElementPlacementResponse placement
    ) implements GroupDto {
        public static SimpleResponse from(Group group) {
            return new SimpleResponse(
                    group.getId(),
                    group.getName(),
                    ElementPlacementResponse.from(group.getPlacement())
            );
        }
    }
}
