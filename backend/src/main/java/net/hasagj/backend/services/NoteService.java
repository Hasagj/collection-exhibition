package net.hasagj.backend.services;

import net.hasagj.backend.models.Note;
import net.hasagj.backend.repositories.NoteRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public Note create(Note note) {
        return repository.save(note);
    }
}
