package ca.ulaval.glo4003.small.http.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.exception.ItemAlreadyExistsException;
import ca.ulaval.glo4003.http.mapper.ItemAlreadyExistsExceptionMapper;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ItemAlreadyExistsExceptionMapperTest {

    public static final String ERROR_MESSAGE = "message";
    private final ItemAlreadyExistsExceptionMapper itemAlreadyExistsExceptionMapper =
        new ItemAlreadyExistsExceptionMapper();

    @Test
    void toResponse_returnsConflict() {
        ItemAlreadyExistsException itemAlreadyExistsException =
            new ItemAlreadyExistsExceptionTest(ERROR_MESSAGE);

        Response actualResponse = itemAlreadyExistsExceptionMapper.toResponse(
            itemAlreadyExistsException
        );

        assertEquals(
            Response.Status.CONFLICT.getStatusCode(),
            actualResponse.getStatus()
        );
    }

    private static class ItemAlreadyExistsExceptionTest
        extends ItemAlreadyExistsException {

        public ItemAlreadyExistsExceptionTest(String message) {
            super(message);
        }
    }
}
