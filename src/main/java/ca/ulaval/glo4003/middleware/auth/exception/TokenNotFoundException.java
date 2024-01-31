package ca.ulaval.glo4003.middleware.auth.exception;

public class TokenNotFoundException extends UnauthorizedException {

    private static final String ERROR_MESSAGE = "Token not found in request.";

    public TokenNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
