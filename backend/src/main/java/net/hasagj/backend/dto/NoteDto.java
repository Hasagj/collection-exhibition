package net.hasagj.backend.dto;

import jakarta.validation.constraints.Size;
import net.hasagj.backend.models.Note;
import net.hasagj.backend.util.Coordinates;
import org.jspecify.annotations.Nullable;

public sealed interface NoteDto {
    record CreateRequest(
            @Nullable @Size(max = 2_000) String description,
            @Nullable Long shelfId,
            @Nullable Coordinates coordinates
    ) implements NoteDto {
        public CreateRequest {
            description = description == null || description.isBlank() ? "Empty note" : description;
            coordinates = coordinates == null ? Coordinates.zero() : coordinates;
        }
    }

    record UpdateRequest(
            @Nullable @Size(max = 2_000) String description,
            @Nullable Long shelfId,
            @Nullable Coordinates coordinates
    ) implements NoteDto {
        public UpdateRequest {
            if (description != null) {
                description = description.isBlank() ? "" : description.trim();
            }
        }
    }

    record Response(
            Long id,
            @Nullable String description,
            Long collectionId,
            ElementPlacementResponse placement
    ) implements NoteDto {
        public static Response from(Note note) {
            return new Response(
                    note.getId(),
                    note.getDescription(),
                    note.getCollection().getId(),
                    ElementPlacementResponse.from(note.getPlacement())
            );
        }
    }
    record SimpleResponse(
            Long id,
            @Nullable String description,
            ElementPlacementResponse placement
    ) implements NoteDto {
        public static SimpleResponse from(Note note) {
            return new SimpleResponse(
                    note.getId(),
                    note.getDescription(),
                    ElementPlacementResponse.from(note.getPlacement())
            );
        }
    }
}
