package ca.ulaval.glo4003.http.mapper;

import ca.ulaval.glo4003.http.response.ExceptionResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    public static final String ERROR_TYPE = "NOT_FOUND";

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(new ExceptionResponse(ERROR_TYPE, exception.getMessage()))
            .build();
    }
}
