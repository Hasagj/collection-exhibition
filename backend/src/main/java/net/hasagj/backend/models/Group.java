package net.hasagj.backend.models;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Group extends Element {
    @OneToOne
    private Photo photo;
    @OneToMany(mappedBy = "group")
    @OrderBy("orderInGroup ASC")
    private ArrayList<Card> cards;
}
