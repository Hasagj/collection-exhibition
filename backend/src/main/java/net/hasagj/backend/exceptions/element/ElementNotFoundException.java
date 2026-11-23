package net.hasagj.backend.exceptions.element;

import net.hasagj.backend.exceptions.ResourceNotFoundException;

public class ElementNotFoundException extends ResourceNotFoundException {
    public ElementNotFoundException(Long id, Long collectionId) {
        super("Element Not Found, ID: " + id + " In Collection, ID: " + collectionId);
    }
}
