package ca.ulaval.glo4003.http.mapper;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.http.response.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ItemNotFoundExceptionMapper
    implements ExceptionMapper<ItemNotFoundException> {

    public static final String ERROR_TYPE = "ITEM_NOT_FOUND";

    @Override
    public Response toResponse(ItemNotFoundException exception) {
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(new ExceptionResponse(ERROR_TYPE, exception.getMessage()))
            .build();
    }
}
