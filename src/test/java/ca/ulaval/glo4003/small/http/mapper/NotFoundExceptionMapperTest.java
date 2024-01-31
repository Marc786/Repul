package ca.ulaval.glo4003.small.http.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.http.mapper.NotFoundExceptionMapper;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class NotFoundExceptionMapperTest {

    public static final String ERROR_MESSAGE = "message";
    private final NotFoundExceptionMapper notFoundExceptionMapper =
        new NotFoundExceptionMapper();

    @Test
    void toResponse_returnsNotFound() {
        NotFoundException notFoundException = new NotFoundException(ERROR_MESSAGE);

        Response actualResponse = notFoundExceptionMapper.toResponse(notFoundException);

        assertEquals(
            Response.Status.NOT_FOUND.getStatusCode(),
            actualResponse.getStatus()
        );
    }
}
