package ca.ulaval.glo4003.small.repul.account.api.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.fixture.account.CustomerProfileFixture;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.api.customer.CustomerProfileResource;
import ca.ulaval.glo4003.repul.account.api.customer.assembler.CustomerProfileAssembler;
import ca.ulaval.glo4003.repul.account.api.customer.dto.request.CreateCustomerProfileRequest;
import ca.ulaval.glo4003.repul.account.api.customer.dto.response.CustomerProfileResponse;
import ca.ulaval.glo4003.repul.account.application.customer.CustomerProfileService;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.Gender;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CustomerProfileResourceTest {

    private static final String EMAIL_STRING = "abc.def.1@ulaval.ca";
    private static final String PASSWORD_STRING = "abc123123";
    private static final String FIRST_NAME_STRING = "Bob";
    private static final String LAST_NAME_STRING = "Martin";
    private static final String GENDER_STRING = "male";
    private static final String IDUL_STRING = "bmart007";
    private static final Gender GENDER = Gender.fromString(GENDER_STRING);
    private static final String URI_PATH = "A/PATH/";
    private static final EmailAddress EMAIL = new EmailAddress(EMAIL_STRING);
    private static final Password PASSWORD = new Password(PASSWORD_STRING);
    private static final Name USER_NAME = new Name(FIRST_NAME_STRING, LAST_NAME_STRING);
    private static final String STUDENT_CARD_NUMBER = "123456789";
    private static final StudentCard STUDENT_CARD = new StudentCard(STUDENT_CARD_NUMBER);
    private static final CustomerProfileId CUSTOMER_ID = new CustomerProfileId(
        IDUL_STRING
    );
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 1, 1);
    private static final CreateCustomerProfileRequest CREATE_CUSTOMER_REQUEST =
        new CreateCustomerProfileRequest(
            EMAIL_STRING,
            PASSWORD_STRING,
            FIRST_NAME_STRING,
            LAST_NAME_STRING,
            BIRTH_DATE.toString(),
            GENDER_STRING,
            IDUL_STRING,
            STUDENT_CARD_NUMBER
        );
    private final CustomerProfile customerProfile = new CustomerProfileFixture().build();

    private final UriInfo uriInfoMock = mock(UriInfo.class);
    private final CustomerProfileService customerProfileServiceMock = mock(
        CustomerProfileService.class
    );
    private final CustomerProfileAssembler customerAssembler =
        new CustomerProfileAssembler();
    private final CustomerProfileResource customerProfileResource =
        new CustomerProfileResource(customerProfileServiceMock, customerAssembler);

    @Test
    void createCustomer_returnsCustomerLocation() {
        when(
            customerProfileServiceMock.createCustomer(
                EMAIL,
                PASSWORD,
                USER_NAME,
                BIRTH_DATE,
                GENDER,
                CUSTOMER_ID,
                STUDENT_CARD
            )
        )
            .thenReturn(CUSTOMER_ID);
        when(uriInfoMock.getAbsolutePathBuilder())
            .thenReturn(UriBuilder.fromPath(URI_PATH));

        Response actualResponse = customerProfileResource.createCustomer(
            CREATE_CUSTOMER_REQUEST,
            uriInfoMock
        );

        assertEquals(Response.Status.CREATED.getStatusCode(), actualResponse.getStatus());
        assertEquals(URI_PATH + IDUL_STRING, actualResponse.getLocation().toString());
    }

    @Test
    void getCustomer_customerIsReturned() {
        CustomerProfileResponse expectedResponse = customerAssembler.toResponse(
            customerProfile
        );
        when(customerProfileServiceMock.getCustomer(CUSTOMER_ID))
            .thenReturn(customerProfile);

        Response actualResponse = customerProfileResource.getCustomer(IDUL_STRING);

        assertEquals(expectedResponse, actualResponse.getEntity());
    }
}
