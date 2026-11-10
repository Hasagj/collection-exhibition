package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByCollectionId(Long id);
}
