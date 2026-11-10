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

    protected ElementPlacement() {}

    public Long getId() { return this.id; }

    public Shelf getShelf() { return this.shelf; }
    public void setShelf(Shelf shelf) { this.shelf = shelf; }

    public Element getElement() { return this.element; }
    protected void setElement(Element element) { this.element = element; }

    public Coordinates getCoordinates() { return this.coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    public void setCoordinates(float x, float y) { this.coordinates = new Coordinates(x, y); }
}
