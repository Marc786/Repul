package ca.ulaval.glo4003.small.http.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.http.mapper.ConstraintViolationExceptionMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ConstraintViolationExceptionMapperTest {

    public static final String ERROR_MESSAGE = "message";
    public static final Set<? extends ConstraintViolation<?>> CONSTRAINT_VIOLATIONS =
        null;
    private final ConstraintViolationExceptionMapper constraintViolationExceptionMapper =
        new ConstraintViolationExceptionMapper();

    @Test
    void toResponse_returnsBadRequest() {
        ConstraintViolationException constraintViolationException =
            new ConstraintViolationException(ERROR_MESSAGE, CONSTRAINT_VIOLATIONS);

        Response actualResponse = constraintViolationExceptionMapper.toResponse(
            constraintViolationException
        );

        assertEquals(
            Response.Status.BAD_REQUEST.getStatusCode(),
            actualResponse.getStatus()
        );
    }
}
