package ca.ulaval.glo4003.small.http.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.exception.InvalidInputException;
import ca.ulaval.glo4003.http.mapper.InvalidInputExceptionMapper;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class InvalidInputExceptionMapperTest {

    public static final String ERROR_MESSAGE = "message";
    private final InvalidInputExceptionMapper invalidInputExceptionMapper =
        new InvalidInputExceptionMapper();

    @Test
    void toResponse_returnsBadRequest() {
        InvalidInputException invalidInputException = new InvalidInputExceptionTest(
            ERROR_MESSAGE
        );

        Response actualResponse = invalidInputExceptionMapper.toResponse(
            invalidInputException
        );

        assertEquals(
            Response.Status.BAD_REQUEST.getStatusCode(),
            actualResponse.getStatus()
        );
    }

    private static class InvalidInputExceptionTest extends InvalidInputException {

        public InvalidInputExceptionTest(String message) {
            super(message);
        }
    }
}
