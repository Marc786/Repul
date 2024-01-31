package ca.ulaval.glo4003.small.http.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.http.mapper.ForbiddenExceptionMapper;
import ca.ulaval.glo4003.middleware.auth.exception.ForbiddenException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ForbiddenExceptionMapperTest {

    private final ForbiddenExceptionMapper forbiddenExceptionMapper =
        new ForbiddenExceptionMapper();

    @Test
    void toResponse_returnsForbidden() {
        ForbiddenException forbiddenException = new ForbiddenException();

        Response actualResponse = forbiddenExceptionMapper.toResponse(forbiddenException);

        assertEquals(
            Response.Status.FORBIDDEN.getStatusCode(),
            actualResponse.getStatus()
        );
    }
}
