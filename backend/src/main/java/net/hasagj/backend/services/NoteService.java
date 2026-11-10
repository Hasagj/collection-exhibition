package net.hasagj.backend.services;

import net.hasagj.backend.models.*;
import net.hasagj.backend.repositories.NoteRepository;
import net.hasagj.backend.util.Coordinates;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public Note create(String name, @Nullable String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        name = name == null || name.isBlank() ? "Untitled" : name.trim();
        description = description == null || description.isBlank() ? null : description;
        Note note = new Note(name, description, collection, shelf, coordinates);
        return repository.save(note);
    }

    public Note create(String name, @Nullable String description, Collection collection, Shelf shelf, float x, float y) {
        name = name == null || name.isBlank() ? "Untitled" : name.trim();
        description = description == null || description.isBlank() ? null : description;
        Note note = new Note(name, description, collection, shelf, x, y);
        return repository.save(note);
    }

    public List<Note> getAll() { return repository.findAll(); }

    public Optional<Note> get(Long id) { return repository.findById(id); }

    public Optional<Note> update(Long id, String name, String description) {
        Optional<Note> note = repository.findById(id);
        if (note.isPresent()) {
            name = name == null || name.isBlank() ? note.get().getName() : name;
            description = description == null ? note.get().getDescription() : description;
            note.get().setName(name);
            note.get().setDescription(description);
            return Optional.of(repository.save(note.get()));
        } else return note;
    }

    public Optional<Note> delete(Long id) {
        Optional<Note> note = repository.findById(id);
        if (note.isPresent()) {
            repository.deleteById(id);
        }
        return note;
    }
}
