package ca.ulaval.glo4003.exception;

public abstract class ItemNotFoundException extends RuntimeException {

    protected ItemNotFoundException(String message) {
        super(message);
    }
}
