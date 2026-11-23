package net.hasagj.backend.models;

import jakarta.persistence.*;
import net.hasagj.backend.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Card extends Element {

    @OneToMany(mappedBy = "card", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("orderInCard ASC")
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne
    private Group group;
    private Integer orderInGroup;

    protected Card() {}

    public Card(String name, String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        super(name, description, collection, shelf, coordinates);
    }

    public List<Photo> getPhotos() { return new ArrayList<>(this.photos); }

    public Photo getPhoto(Integer position) {
        return position == null ||  position < 0 || this.photos.size() <= position
                ? null
                : this.photos.get(position);
    }

    public boolean hasPhoto(Photo photo) { return photo != null && this.photos.contains(photo); }

    public boolean addPhoto(Photo photo, Integer position) {
        if (position == null || position < 0 || this.photos.size() < position || photo == null) return false;
        if (position == this.photos.size()) {
            this.photos.add(photo);
            return this.updatePositions();
        }
        List<Photo> left = new ArrayList<>(this.photos.subList(0, position));
        List<Photo> right = new ArrayList<>(this.photos.subList(position, this.photos.size()));
        left.add(photo);
        left.addAll(right);
        this.photos.clear();
        this.photos.addAll(left);
        return this.updatePositions();
    }

    public boolean removePhoto(Photo photo) {
        if (!this.hasPhoto(photo)) return false;
        this.photos.remove(photo);
        this.updatePositions();
        photo.setCard(null);
        photo.setOrderInCard(null);
        return true;
    }

    private boolean updatePositions() {
        int i = 0;
        for (Photo photo : this.photos) {
            photo.setCard(this);
            photo.setOrderInCard(i);
            i++;
        }
        return true;
    }

    public Group getGroup() { return this.group; }
    protected void setGroup(Group group) { this.group = group; }

    public Integer getOrderInGroup() { return this.orderInGroup; }
    protected void setOrderInGroup(Integer orderInGroup) { this.orderInGroup = orderInGroup; }
}
