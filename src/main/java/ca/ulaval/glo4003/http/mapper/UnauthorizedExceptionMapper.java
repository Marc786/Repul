package ca.ulaval.glo4003.http.mapper;

import ca.ulaval.glo4003.http.response.ExceptionResponse;
import ca.ulaval.glo4003.middleware.auth.exception.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper
    implements ExceptionMapper<UnauthorizedException> {

    public static final String ERROR_TYPE = "UNAUTHORIZED";

    @Override
    public Response toResponse(UnauthorizedException e) {
        return Response
            .status(Response.Status.UNAUTHORIZED)
            .entity(new ExceptionResponse(ERROR_TYPE, e.getMessage()))
            .build();
    }
}
