package net.hasagj.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import net.hasagj.backend.util.Coordinates;

import java.util.List;
import java.util.Map;

@Entity
public class Shelf extends Element {
    @OneToMany(mappedBy = "shelf")
    private List<ElementPlacement> placements;
}
