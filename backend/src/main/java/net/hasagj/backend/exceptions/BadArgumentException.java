package net.hasagj.backend.exceptions;

public abstract class BadArgumentException extends IllegalArgumentException {
    public BadArgumentException(String message) { super(message); }
}
