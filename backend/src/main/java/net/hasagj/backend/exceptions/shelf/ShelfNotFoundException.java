package net.hasagj.backend.exceptions.shelf;

import net.hasagj.backend.exceptions.ResourceNotFoundException;

public class ShelfNotFoundException extends ResourceNotFoundException {
    public ShelfNotFoundException(Long id, Long collectionId) {
        super("Shelf Not Found, ID: " + id + " In Collection, ID: " + collectionId);
    }
}
