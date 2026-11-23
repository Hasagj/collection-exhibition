package net.hasagj.backend.models;

import jakarta.persistence.*;
import net.hasagj.backend.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "element_group")
public class Group extends Element {
    @OneToOne(mappedBy = "group", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Photo photo;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("orderInGroup ASC")
    private List<Card> cards = new ArrayList<>();

    protected Group() {}

    public Group(String name, String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        super(name, description, collection, shelf, coordinates);
    }

    public Photo getPhoto() { return this.photo; }
    public void setPhoto(Photo photo) {
        if (photo == null && this.photo != null) {
            this.photo.setGroup(null);
            this.photo = null;
            return;
        }
        this.photo = photo;
        photo.setGroup(this);
    }

    public List<Card> getCards() { return new ArrayList<>(this.cards); }

    public Card getCard(Integer position) {
        return position == null || position < 0 || this.cards.size() <= position
                ? null
                : this.cards.get(position);
    }

    public boolean hasCard(Card card) {
        return this.cards.contains(card);
    }

    public boolean addCard(Card card, Integer position) {
        if (position == null || position < 0 || this.cards.size() < position || card == null) return false;
        if (position == this.cards.size()) {
            this.cards.add(card);
            return this.updatePositions();
        }
        List<Card> left = new ArrayList<>(this.cards.subList(0, position));
        List<Card> right = new ArrayList<>(this.cards.subList(position, this.cards.size()));
        left.add(card);
        left.addAll(right);
        this.cards.clear();
        this.cards.addAll(left);
        return this.updatePositions();
    }

    public boolean removeCard(Card card) {
        if (!this.hasCard(card)) return false;
        this.cards.remove(card);
        this.updatePositions();
        card.setGroup(null);
        card.setOrderInGroup(null);
        return true;
    }

    private boolean updatePositions() {
        int i = 0;
        for (Card card : this.cards) {
            card.setGroup(this);
            card.setOrderInGroup(i);
            i++;
        }
        return true;
    }
}
