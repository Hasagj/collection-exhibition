package net.hasagj.backend.dto;

import net.hasagj.backend.models.Photo;
import org.jspecify.annotations.Nullable;

public sealed interface PhotoDto {
    record Response(
            Long id,
            String path,
            @Nullable Long cardId,
            @Nullable Long groupId
    ) implements PhotoDto {
        public static Response from(Photo photo) {
            return new Response(photo.getId(), photo.getPath(), photo.getCard().getId(), photo.getGroup().getId());
        }
    }

    record SimpleResponse(
            Long id,
            String path
    ) implements PhotoDto {
        public static SimpleResponse from(Photo photo) {
            return new SimpleResponse(photo.getId(), photo.getPath());
        }
    }
}
