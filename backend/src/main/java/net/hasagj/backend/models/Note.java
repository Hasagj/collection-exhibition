package net.hasagj.backend.models;

import jakarta.persistence.Entity;

import java.util.ArrayList;

@Entity
public class Note extends Element {
    private ArrayList<Element> relatedElements;
}
