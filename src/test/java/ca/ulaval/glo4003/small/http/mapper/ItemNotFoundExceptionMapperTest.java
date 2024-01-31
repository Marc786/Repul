package ca.ulaval.glo4003.small.http.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.http.mapper.ItemNotFoundExceptionMapper;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ItemNotFoundExceptionMapperTest {

    public static final String ERROR_MESSAGE = "message";
    private final ItemNotFoundExceptionMapper itemNotFoundExceptionMapper =
        new ItemNotFoundExceptionMapper();

    @Test
    void toResponse_returnsNotFound() {
        ItemNotFoundException itemAlreadyExistsException = new ItemNotFoundExceptionTest(
            ERROR_MESSAGE
        );

        Response actualResponse = itemNotFoundExceptionMapper.toResponse(
            itemAlreadyExistsException
        );

        assertEquals(
            Response.Status.NOT_FOUND.getStatusCode(),
            actualResponse.getStatus()
        );
    }

    private static class ItemNotFoundExceptionTest extends ItemNotFoundException {

        public ItemNotFoundExceptionTest(String message) {
            super(message);
        }
    }
}
