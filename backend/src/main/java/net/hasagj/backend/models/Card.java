package net.hasagj.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;

@Entity
public class Card extends Element {
    @OneToMany(mappedBy = "card")
    private ArrayList<Photo> photos;
    @ManyToOne
    private Group group;
    private Integer orderInGroup;
    /* поля */
    /* классификации */
}
