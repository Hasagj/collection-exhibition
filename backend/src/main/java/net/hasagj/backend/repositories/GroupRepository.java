package net.hasagj.backend.repositories;

import net.hasagj.backend.dto.GroupDto;
import net.hasagj.backend.models.Group;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<GroupDto.SimpleResponse> findAllByCollectionId(Long collectionId);
    Optional<Group> findByIdAndCollectionId(Long id, Long collectionId);
    @EntityGraph(attributePaths = "cards")
    Optional<Group> findWithCardsByIdAndCollectionId(Long id, Long collectionId);
}
