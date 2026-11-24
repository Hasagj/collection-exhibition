package net.hasagj.backend.exceptions.photo;

import net.hasagj.backend.exceptions.BadArgumentException;

public class PhotoAddingException extends BadArgumentException {
    public PhotoAddingException(String path, Long ownerId) {
        super("Cannot add selected photo (Path: \"" + path + "\"), cause it's owned by other card (ID: " + ownerId + ")");
    }
}
