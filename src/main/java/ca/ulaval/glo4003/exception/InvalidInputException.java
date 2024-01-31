package ca.ulaval.glo4003.exception;

public abstract class InvalidInputException extends RuntimeException {

    protected InvalidInputException(String message) {
        super(message);
    }
}
