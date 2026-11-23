package net.hasagj.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import net.hasagj.backend.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Note extends Element {

    @ManyToMany
    private List<Element> elements = new ArrayList<>();

    protected Note() {}

    public Note(String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        super(null , description, collection, shelf, coordinates);
    }

    public List<Element> getElements() {
        return new ArrayList<>(this.elements);
    }

    public boolean addElement(Element element) {
        if (element instanceof Note ||
                element instanceof Shelf shelf && shelf.isRoot()) return false;
        this.elements.add(element);
        return true;
    }

    public boolean hasElement(Element element) {
        return this.elements.contains(element);
    }

    public boolean removeElement(Element element) {
        return this.elements.remove(element);
    }

    public void removeAllElements() {
        this.elements.clear();
    }
}
