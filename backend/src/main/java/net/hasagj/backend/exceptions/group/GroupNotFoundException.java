package net.hasagj.backend.exceptions.group;

import net.hasagj.backend.exceptions.ResourceNotFoundException;

public class GroupNotFoundException extends ResourceNotFoundException {
    public GroupNotFoundException(Long id, Long collectionId) {
        super("Group Not Found, ID: " + id + " In Collection, ID: " + collectionId);
    }
}
