package net.hasagj.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import net.hasagj.backend.util.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class Shelf extends Element {
    @OneToMany(mappedBy = "shelf")
    private List<ElementPlacement> placements = new ArrayList<>();

    protected Shelf() {}

    public Shelf(String name, String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        super(name, description, collection, shelf, coordinates);
    }

    public static Shelf createRoot(String name, String description, Collection collection) {
        Shelf root = new Shelf();
        root.setName(name);
        root.setDescription(description);
        root.setCollection(collection);
        return root;
    }

    public List<ElementPlacement> getPlacements() { return new ArrayList<>(this.placements); }
    public ElementPlacement getPlacement(int index) {
        return index < 0 || this.placements.size() <= index
                ? null
                : this.placements.get(index);
    }
    public void addPlacement(ElementPlacement placement) {
        this.placements.add(placement);
        placement.setShelf(this);
    }
    public boolean removePlacement(int index) {
        if (index < 0 || this.placements.size() <= index) {
            return false;
        } else {
            this.placements.get(index).setShelf(null);
            this.placements.remove(index);
            return true;
        }
    }

}
