package net.hasagj.backend.models;

import jakarta.persistence.Entity;
import net.hasagj.backend.util.Coordinates;

import java.util.List;

@Entity
public class Note extends Element {

    protected Note() {}

    public Note(String name, String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        super(name, description, collection, shelf, coordinates);
    }

    public Note(String name, String description, Collection collection, Shelf shelf, float x, float y) {
        super(name, description, collection, shelf, x, y);
    }

    @Override
    public List<Note> getNotes() { return List.of(); }

    @Override
    public Note getNote(int index) { return null; }

    @Override
    public void addNote(Note note) {}

    @Override
    public boolean removeNote(int index) { return false; }
}
