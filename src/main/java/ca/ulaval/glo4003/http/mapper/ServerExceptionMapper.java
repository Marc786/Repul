package ca.ulaval.glo4003.http.mapper;

import ca.ulaval.glo4003.http.response.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServerExceptionMapper implements ExceptionMapper<RuntimeException> {

    public static final String ERROR_TYPE = "SERVER_ERROR";
    public static final String ERROR_MESSAGE = "An internal error occurred";

    @Override
    public Response toResponse(RuntimeException exception) {
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new ExceptionResponse(ERROR_TYPE, ERROR_MESSAGE))
            .build();
    }
}
