package net.hasagj.backend.util;

import jakarta.persistence.Embeddable;

@Embeddable
public record Coordinates(float x, float y) {
    public static Coordinates zero() {
        return new Coordinates(0F, 0F);
    }
}
