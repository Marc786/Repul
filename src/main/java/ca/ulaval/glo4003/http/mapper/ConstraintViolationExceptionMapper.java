package ca.ulaval.glo4003.http.mapper;

import ca.ulaval.glo4003.http.response.ExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {

    public static final String ERROR_TYPE = "CONSTRAINT_VIOLATION";

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_TYPE, exception.getMessage()))
            .build();
    }
}
