package net.hasagj.backend.util;

import jakarta.persistence.Embeddable;

@Embeddable
public record Coordinates(float x, float y) { }
