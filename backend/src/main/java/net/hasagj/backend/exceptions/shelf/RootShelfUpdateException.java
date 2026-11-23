package net.hasagj.backend.exceptions.shelf;

import net.hasagj.backend.exceptions.BadArgumentException;

public class RootShelfUpdateException extends BadArgumentException {
    public RootShelfUpdateException(Long id, Long collectionId) {
        super("Cannot UPDATE root shelf (ID: " + id + ") of collection (ID: " + collectionId + ")");
    }
}
