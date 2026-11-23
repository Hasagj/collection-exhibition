package net.hasagj.backend.exceptions.placement;

import net.hasagj.backend.exceptions.BadArgumentException;

public class BadElementMovingException extends BadArgumentException {
    public BadElementMovingException(Long elementId, Long shelfId) {
        super("Cannot put element (ID: " + elementId + ") inside shelf (ID: " + shelfId + ")");
    }
}
