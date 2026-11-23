package net.hasagj.backend.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import net.hasagj.backend.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Shelf extends Element {
    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ElementPlacement> innerPlacements = new ArrayList<>();

    protected Shelf() {}

    public Shelf(String name, String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        super(name, description, collection, shelf, coordinates);
    }

    public static Shelf createRoot(Collection collection) {
        Shelf root = new Shelf();
        root.setName("Root of collection: " + collection.getId());
        root.setDescription("This is the root shelf");
        root.setCollection(collection);
        return root;
    }

    public boolean isRoot() {
        return this.getId().equals(this.getCollection().getRootShelf().getId());
    }

    public List<ElementPlacement> getInnerPlacements() { return new ArrayList<>(this.innerPlacements); }
    public ElementPlacement getInnerPlacement(int index) {
        return index < 0 || this.innerPlacements.size() <= index
                ? null
                : this.innerPlacements.get(index);
    }
    public void addPlacement(ElementPlacement placement) {
        this.innerPlacements.add(placement);
        placement.setShelf(this);
    }
    public boolean removePlacement(int index) {
        if (index < 0 || this.innerPlacements.size() <= index) {
            return false;
        } else {
            this.innerPlacements.get(index).setShelf(null);
            this.innerPlacements.remove(index);
            return true;
        }
    }

}
