package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByCollectionId(Long id);
}