package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
