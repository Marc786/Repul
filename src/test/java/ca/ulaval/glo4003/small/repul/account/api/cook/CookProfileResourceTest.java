package ca.ulaval.glo4003.small.repul.account.api.cook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.api.cook.CookProfileResource;
import ca.ulaval.glo4003.repul.account.api.cook.dto.request.CreateCookProfileRequest;
import ca.ulaval.glo4003.repul.account.application.cook.CookProfileService;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileId;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CookProfileResourceTest {

    private static final CookProfileId COOK_ID = new CookProfileId();
    private static final String URI_PATH = "A/PATH/";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL_STRING = "email";
    private static final String PASSWORD_STRING = "password";
    private static final Name NAME = new Name(FIRST_NAME, LAST_NAME);
    private static final EmailAddress EMAIL = new EmailAddress(EMAIL_STRING);
    private static final Password PASSWORD = new Password(PASSWORD_STRING);
    private static final CreateCookProfileRequest CREATE_COOK_REQUEST =
        new CreateCookProfileRequest(
            FIRST_NAME,
            LAST_NAME,
            EMAIL_STRING,
            PASSWORD_STRING
        );

    private final CookProfileService cookProfileServiceMock = mock(
        CookProfileService.class
    );
    private final UriInfo uriInfoMock = mock(UriInfo.class);

    private CookProfileResource cookProfileResource;

    @BeforeEach
    void setup() {
        cookProfileResource = new CookProfileResource(cookProfileServiceMock);
    }

    @Test
    void createCook_returnsCreatedStatusCode() {
        when(cookProfileServiceMock.createCook(NAME, EMAIL, PASSWORD))
            .thenReturn(COOK_ID);
        when(uriInfoMock.getAbsolutePathBuilder())
            .thenReturn(UriBuilder.fromUri(URI_PATH));

        Response actualResponse = cookProfileResource.createCook(
            CREATE_COOK_REQUEST,
            uriInfoMock
        );

        assertEquals(Response.Status.CREATED.getStatusCode(), actualResponse.getStatus());
        assertEquals(URI_PATH + COOK_ID, actualResponse.getLocation().toString());
    }
}
