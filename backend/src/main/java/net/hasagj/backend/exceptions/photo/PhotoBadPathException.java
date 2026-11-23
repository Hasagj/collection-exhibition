package net.hasagj.backend.exceptions.photo;

import net.hasagj.backend.exceptions.BadArgumentException;

public class PhotoBadPathException extends BadArgumentException {
    public PhotoBadPathException(String path) {
        super("Path \"" + path + "\" has wrong format");
    }
}
