package net.hasagj.backend.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Element {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    private Collection collection;

    @OneToOne(mappedBy = "element")
    private ElementPlacement placement;

    @ManyToMany
    private List<Note> notes;
}
