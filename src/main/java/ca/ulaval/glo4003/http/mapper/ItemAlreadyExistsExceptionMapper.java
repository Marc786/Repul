package ca.ulaval.glo4003.http.mapper;

import ca.ulaval.glo4003.exception.ItemAlreadyExistsException;
import ca.ulaval.glo4003.http.response.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ItemAlreadyExistsExceptionMapper
    implements ExceptionMapper<ItemAlreadyExistsException> {

    public static final String ERROR_TYPE = "ITEM_ALREADY_EXISTS";

    @Override
    public Response toResponse(ItemAlreadyExistsException exception) {
        return Response
            .status(Response.Status.CONFLICT)
            .entity(new ExceptionResponse(ERROR_TYPE, exception.getMessage()))
            .build();
    }
}
