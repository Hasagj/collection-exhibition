package net.hasagj.backend.exceptions.photo;

import net.hasagj.backend.exceptions.ResourceNotFoundException;

public class PhotoNotFoundException extends ResourceNotFoundException {
    public PhotoNotFoundException(String path) {
        super("Photo Not Found, path: \"" + path + "\"");
    }
    public PhotoNotFoundException(String path, Long elementId) {
        super("Element (ID: " + elementId + ") do NOT contains photo (Path: " + path + ")");
    }
    public PhotoNotFoundException(Long elementId) {
        super("Element (ID: " + elementId + ") do NOT contains photo");
    }
}
