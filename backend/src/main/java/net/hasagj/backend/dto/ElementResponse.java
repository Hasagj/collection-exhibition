package net.hasagj.backend.dto;

import net.hasagj.backend.models.Element;

public record ElementResponse(
        String name,
        String description,
        String type,
        Long collectionId
) {
    public ElementResponse {

    }

    public static ElementResponse from(Element element) {
        return new ElementResponse(element.getName(), element.getDescription(), )
    }
}
