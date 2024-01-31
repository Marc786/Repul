package ca.ulaval.glo4003.small.http.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.http.mapper.ServerExceptionMapper;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ServerExceptionMapperTest {

    public static final String ERROR_MESSAGE = "message";
    private final ServerExceptionMapper serverExceptionMapper =
        new ServerExceptionMapper();

    @Test
    void toResponse_returnsInternalServerError() {
        RuntimeException exception = new RuntimeException(ERROR_MESSAGE);

        Response actualResponse = serverExceptionMapper.toResponse(exception);

        assertEquals(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            actualResponse.getStatus()
        );
    }
}
