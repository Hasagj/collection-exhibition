package net.hasagj.backend.models;

import jakarta.persistence.*;
import net.hasagj.backend.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "element_group")
public class Group extends Element {
    @OneToOne(mappedBy = "group")
    private Photo photo;

    @OneToMany(mappedBy = "group")
    @OrderBy("orderInGroup ASC")
    private List<Card> cards = new ArrayList<>();

    protected Group() {}

    public Group(String name, String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        super(name, description, collection, shelf, coordinates);
    }

    public Group(String name, String description, Collection collection, Shelf shelf, float x, float y) {
        super(name, description, collection, shelf, x, y);
    }

    public Photo getPhoto() { return this.photo; }
    public void setPhoto(Photo photo) {
        this.photo = photo;
        photo.setGroup(this);
    }

    public List<Card> getCards() { return new ArrayList<>(this.cards); }
    public Card getCard(int index) {
        return index < 0 || this.cards.size() <= index
                ? null
                : this.cards.get(index);
    }
    public boolean addCard(Card card, int position) {
        if (position < 0 || this.cards.size() < position || card == null) return false;
        if (position == this.cards.size()) {
            this.cards.add(card);
            return this.update();
        }
        List<Card> left = new ArrayList<>(this.cards.subList(0, position));
        List<Card> right = new ArrayList<>(this.cards.subList(position, this.cards.size()));
        left.add(card);
        left.addAll(right);
        this.cards.clear();
        this.cards.addAll(left);
        return this.update();
    }
    public boolean removeCard(int index) {
        if (index < 0 || this.cards.size() <= index) {
            return false;
        } else {
            this.cards.get(index).setGroup(null);
            this.cards.get(index).setOrderInGroup(null);
            this.cards.remove(index);
            return this.update();
        }
    }
    private boolean update() {
        int i = 0;
        for (Card card : this.cards) {
            card.setGroup(this);
            card.setOrderInGroup(i);
            i++;
        }
        return true;
    }
}
