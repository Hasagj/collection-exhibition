package net.hasagj.backend.dto;

public record ElementResponse(
        Long id,
        String name,
        String description,
        String type,
        Long collectionId
) {}
