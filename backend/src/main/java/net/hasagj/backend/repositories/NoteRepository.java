package net.hasagj.backend.repositories;

import net.hasagj.backend.dto.NoteDto;
import net.hasagj.backend.models.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<NoteDto.SimpleResponse> findAllByCollectionId(Long collectionId);
    Optional<Note> findByIdAndCollectionId(Long id, Long collectionId);
    @EntityGraph(attributePaths = "elements")
    Optional<Note> findWithElementsByIdAndCollectionId(Long id, Long collectionId);
}