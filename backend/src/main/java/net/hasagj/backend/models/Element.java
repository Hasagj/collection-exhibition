package net.hasagj.backend.models;

import jakarta.persistence.*;
import net.hasagj.backend.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Collection collection;

    @OneToOne(mappedBy = "element", cascade = CascadeType.PERSIST)
    private ElementPlacement placement;

    @ManyToMany
    private List<Note> notes = new ArrayList<>();

    protected Element() {}

    public Element(String name, String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        this.name = name;
        this.description = description;
        this.collection = collection;
        ElementPlacement placement = new ElementPlacement();
        this.placement = placement;
        shelf.addPlacement(placement);
        placement.setElement(this);
        placement.setCoordinates(coordinates);
    }

    public Element(String name, String description, Collection collection, Shelf shelf, float x, float y) {
        this.name = name;
        this.description = description;
        this.collection = collection;
        ElementPlacement placement = new ElementPlacement();
        this.placement = placement;
        shelf.addPlacement(placement);
        placement.setElement(this);
        placement.setCoordinates(x, y);
    }

    public Long getId() { return this.id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public Collection getCollection() { return this.collection; }
    protected void setCollection(Collection collection) { this.collection = collection; }

    public ElementPlacement getPlacement() { return this.placement; }
    public void setPlacement(ElementPlacement placement) {
        this.placement = placement;
        placement.setElement(this);
    }

    public List<Note> getNotes() { return new ArrayList<>(this.notes); }
    public Note getNote(int index) {
        return index < 0 || this.notes.size() <= index
                ? null
                : this.notes.get(index);
    }
    public void addNote(Note note) { this.notes.add(note); }
    public boolean removeNote(int index) {
        if (index < 0 || this.notes.size() <= index) {
            return false;
        } else {
            this.notes.remove(index);
            return true;
        }
    }
}
