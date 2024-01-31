package ca.ulaval.glo4003.small.http.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.http.mapper.UnauthorizedExceptionMapper;
import ca.ulaval.glo4003.middleware.auth.exception.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class UnauthorizedExceptionMapperTest {

    public static final String ERROR_MESSAGE = "test";
    private final UnauthorizedExceptionMapper unauthorizedExceptionMapper =
        new UnauthorizedExceptionMapper();

    @Test
    void toResponse_returnsUnauthorized() {
        UnauthorizedException unauthorizedException = new UnauthorizedExceptionTest(
            ERROR_MESSAGE
        );

        Response actualResponse = unauthorizedExceptionMapper.toResponse(
            unauthorizedException
        );

        assertEquals(
            Response.Status.UNAUTHORIZED.getStatusCode(),
            actualResponse.getStatus()
        );
    }

    private static class UnauthorizedExceptionTest extends UnauthorizedException {

        public UnauthorizedExceptionTest(String message) {
            super(message);
        }
    }
}
