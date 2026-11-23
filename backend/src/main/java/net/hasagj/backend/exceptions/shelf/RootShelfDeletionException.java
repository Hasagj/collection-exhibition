package net.hasagj.backend.exceptions.shelf;

import net.hasagj.backend.exceptions.BadArgumentException;

public class RootShelfDeletionException extends BadArgumentException {
    public RootShelfDeletionException(Long id, Long collectionId) {
        super("Cannot DELETE root shelf (ID: " + id + ") of collection (ID: " + collectionId + ")");
    }
}
