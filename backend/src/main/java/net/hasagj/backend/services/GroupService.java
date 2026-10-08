package net.hasagj.backend.services;

import net.hasagj.backend.models.Group;
import net.hasagj.backend.repositories.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupRepository repository;

    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public Group create(Group group) {
        return repository.save(group);
    }
}
