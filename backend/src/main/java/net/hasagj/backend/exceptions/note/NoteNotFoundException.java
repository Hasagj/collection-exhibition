package net.hasagj.backend.exceptions.note;

import net.hasagj.backend.exceptions.ResourceNotFoundException;

public class NoteNotFoundException extends ResourceNotFoundException {
    public NoteNotFoundException(Long id, Long collectionId) {
        super("Note Not Found, ID: " + id + " In Collection, ID: " + collectionId);
    }
}
