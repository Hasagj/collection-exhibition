package net.hasagj.backend.exceptions.note;

import net.hasagj.backend.exceptions.BadArgumentException;

public class InvalidNoteTargetException extends BadArgumentException {
    public InvalidNoteTargetException(Long id) {
        super("Cannot attach note (ID: " + id + ") to itself");
    }
    public InvalidNoteTargetException(Long noteId, Long otherNoteId) {
        super("Cannot attach note (ID: " + noteId + ") to another note (ID: " + otherNoteId + ")");
    }
}
