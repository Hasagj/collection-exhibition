package net.hasagj.backend.services;

import net.hasagj.backend.models.*;
import net.hasagj.backend.repositories.GroupRepository;
import net.hasagj.backend.util.Coordinates;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository repository;

    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public Group create(String name, @Nullable String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        name = name == null || name.isBlank() ? "Untitled" : name.trim();
        description = description == null || description.isBlank() ? null : description;
        Group group = new Group(name, description, collection, shelf, coordinates);
        return repository.save(group);
    }

    public Group create(String name, @Nullable String description, Collection collection, Shelf shelf, float x, float y) {
        name = name == null || name.isBlank() ? "Untitled" : name.trim();
        description = description == null || description.isBlank() ? null : description;
        Group group = new Group(name, description, collection, shelf, x, y);
        return repository.save(group);
    }

    public List<Group> getAll() { return repository.findAll(); }

    public Optional<Group> get(Long id) { return repository.findById(id); }

    public Optional<Group> update(Long id, String name, String description, Photo photo) {
        Optional<Group> group = repository.findById(id);
        if (group.isPresent()) {
            name = name == null || name.isBlank() ? group.get().getName() : name;
            description = description == null ? group.get().getDescription() : description;
            photo = photo == null || photo == group.get().getPhoto() ? group.get().getPhoto() : photo;
            group.get().setName(name);
            group.get().setDescription(description);
            if (photo != null) group.get().setPhoto(photo);
            return Optional.of(repository.save(group.get()));
        } else return group;
    }

    public Optional<Group> delete(Long id) {
        Optional<Group> group = repository.findById(id);
        if (group.isPresent()) {
            repository.deleteById(id);
        }
        return group;
    }
}
