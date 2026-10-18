package net.hasagj.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import net.hasagj.backend.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Card extends Element {

    @OneToMany(mappedBy = "card")
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne
    private Group group;
    private Integer orderInGroup;

    protected Card() {}

    public Card(String name, String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        super(name, description, collection, shelf, coordinates);
    }

    public Card(String name, String description, Collection collection, Shelf shelf, float x, float y) {
        super(name, description, collection, shelf, x, y);
    }

    public List<Photo> getPhotos() { return new ArrayList<>(this.photos); }
    public Photo getPhoto(int index) {
        return index < 0 || this.photos.size() <= index
                ? null
                : this.photos.get(index);
    }
    public boolean addPhoto(Photo photo, int position) {
        if (position < 0 || this.photos.size() < position || photo == null) return false;
        if (position == this.photos.size()) {
            this.photos.add(photo);
            return update();
        }
        List<Photo> left = new ArrayList<>(this.photos.subList(0, position));
        List<Photo> right = new ArrayList<>(this.photos.subList(position, this.photos.size()));
        left.add(photo);
        left.addAll(right);
        this.photos.clear();
        this.photos.addAll(left);
        return this.update();
    }
    public boolean removePhoto(int index) {
        if (index < 0 || this.photos.size() <= index) {
            return false;
        } else {
            this.photos.get(index).setCard(null);
            this.photos.get(index).setOrderInCard(null);
            this.photos.remove(index);
            return this.update();
        }
    }
    public boolean update() {
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

    /* _____to do_____ */
    /*  custom fields  */
    /* classifications */
}
