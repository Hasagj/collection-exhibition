package net.hasagj.backend.exceptions.photo;

import net.hasagj.backend.exceptions.BadArgumentException;

public class PhotoBadPositionException extends BadArgumentException {
    public PhotoBadPositionException(Integer position) {
        super("Cannot add photo cause its position is out of range or NULL (position: " + position + ")");
    }
}
