package net.hasagj.backend.exceptions.card;

import net.hasagj.backend.exceptions.ResourceNotFoundException;

public class CardNotFoundException extends ResourceNotFoundException {
    public CardNotFoundException(Long id, Long collectionId) {
        super("Card Not Found, ID: " + id + " In Collection, ID: " + collectionId);
    }
    public CardNotFoundException(Long id, Long collectionId, Long groupId) {
        super("Card Not Found, ID: " + id + "Inside group, ID: " + groupId);
    }
}
