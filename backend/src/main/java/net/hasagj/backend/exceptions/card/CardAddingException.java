package net.hasagj.backend.exceptions.card;

import net.hasagj.backend.exceptions.BadArgumentException;

public class CardAddingException extends BadArgumentException {
    public CardAddingException(Long id, Long ownerId) {
        super("Cannot add selected card (PID: \"" + id + "\"), cause it's owned by other card (ID: " + ownerId + ")");
    }
}
