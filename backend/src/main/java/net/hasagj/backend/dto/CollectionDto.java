package net.hasagj.backend.dto;

import jakarta.validation.constraints.Size;
import net.hasagj.backend.models.Collection;
import org.jspecify.annotations.Nullable;

public sealed interface CollectionDto {
    record CreateRequest(
            @Nullable @Size(max = 100) String name,
            @Nullable @Size(max = 10_000) String description
    ) implements CollectionDto {
        public CreateRequest {
            name = name == null || name.isBlank() ? "Untitled" : name.trim();
            description = description == null || description.isBlank() ? null : description;
        }
    }

    record UpdateRequest(
            @Nullable @Size(max = 100) String name,
            @Nullable @Size(max = 10_000) String description
    ) implements CollectionDto {
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
            @Nullable String description
    ) implements CollectionDto {
        public static Response from(Collection collection) {
            return new Response(collection.getId(), collection.getName(), collection.getDescription());
        }
    }

    record SimpleResponse(
            Long id,
            String name
    ) implements CollectionDto {
        public static SimpleResponse from(Collection collection) {
            return new SimpleResponse(collection.getId(), collection.getName());
        }
    }
}
