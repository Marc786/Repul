package ca.ulaval.glo4003.exception;

public abstract class ItemAlreadyExistsException extends RuntimeException {

    protected ItemAlreadyExistsException(String message) {
        super(message);
    }
}
