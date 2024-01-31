package ca.ulaval.glo4003.http.mapper;

import ca.ulaval.glo4003.http.response.ExceptionResponse;
import ca.ulaval.glo4003.middleware.auth.exception.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    private static final String ERROR_TYPE = "FORBIDDEN";

    @Override
    public Response toResponse(ForbiddenException e) {
        return Response
            .status(Response.Status.FORBIDDEN)
            .entity(new ExceptionResponse(ERROR_TYPE, e.getMessage()))
            .build();
    }
}
