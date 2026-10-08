package net.hasagj.backend.models;

import jakarta.persistence.*;
import net.hasagj.backend.util.Coordinates;

@Entity
public class ElementPlacement {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Shelf shelf;

    @OneToOne
    private Element element;

    @Embedded
    private Coordinates coordinates;
}
