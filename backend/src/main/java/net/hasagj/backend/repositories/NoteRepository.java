package net.hasagj.backend.repositories;

import net.hasagj.backend.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
