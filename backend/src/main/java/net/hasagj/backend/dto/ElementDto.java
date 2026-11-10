package net.hasagj.backend.dto;

public record ElementDto(
        String name,
        String description,
        String type,
        Long collectionId
) { }
