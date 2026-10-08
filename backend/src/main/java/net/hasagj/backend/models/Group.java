package net.hasagj.backend.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Group extends Element {
    @OneToOne
    private Photo photo;

    @OneToMany(mappedBy = "group")
    @OrderBy("orderInGroup ASC")
    private List<Card> cards;
}
