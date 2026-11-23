package net.hasagj.backend.exceptions.card;

import net.hasagj.backend.exceptions.BadArgumentException;

public class CardBadPositionException extends BadArgumentException {
    public CardBadPositionException(Integer position) {
        super("Cannot add card cause its position is out of range or NULL (position: " + position + ")");
    }
}
