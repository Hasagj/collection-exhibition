package net.hasagj.backend.exceptions.collection;

import net.hasagj.backend.exceptions.ResourceNotFoundException;

public class CollectionNotFoundException extends ResourceNotFoundException {
    public CollectionNotFoundException(Long id) {
        super("Collection Not Found, ID: " + id);
    }
}
