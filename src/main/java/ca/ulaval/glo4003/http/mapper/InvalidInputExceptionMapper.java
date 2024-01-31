package ca.ulaval.glo4003.http.mapper;

import ca.ulaval.glo4003.exception.InvalidInputException;
import ca.ulaval.glo4003.http.response.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidInputExceptionMapper
    implements ExceptionMapper<InvalidInputException> {

    public static final String ERROR_TYPE = "INVALID_INPUT";

    @Override
    public Response toResponse(InvalidInputException exception) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_TYPE, exception.getMessage()))
            .build();
    }
}
