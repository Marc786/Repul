package ca.ulaval.glo4003.small.repul.account.api.carrier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.api.carrier.CarrierProfileResource;
import ca.ulaval.glo4003.repul.account.api.carrier.dto.request.CreateCarrierProfileRequest;
import ca.ulaval.glo4003.repul.account.application.carrier.CarrierProfileService;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileId;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Test;

class CarrierProfileResourceTest {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email@ulaval.ca";
    private static final String A_PASSWORD = "password";
    private static final CreateCarrierProfileRequest CREATE_CARRIER_REQUEST =
        new CreateCarrierProfileRequest(FIRST_NAME, LAST_NAME, EMAIL, A_PASSWORD);
    private static final Name NAME = new Name(FIRST_NAME, LAST_NAME);
    private static final EmailAddress EMAIL_ADDRESS = new EmailAddress(EMAIL);
    private static final Password PASSWORD = new Password(A_PASSWORD);
    private static final CarrierProfileId CARRIER_ID = new CarrierProfileId();
    private static final String URI_PATH = "A/PATH/";

    private final CarrierProfileService carrierProfileServiceMock = mock(
        CarrierProfileService.class
    );
    private final UriInfo uriInfoMock = mock(UriInfo.class);

    private final CarrierProfileResource carrierProfileResource =
        new CarrierProfileResource(carrierProfileServiceMock);

    @Test
    void createCarrier_returnsCreatedStatusCode() {
        when(carrierProfileServiceMock.createCarrier(NAME, EMAIL_ADDRESS, PASSWORD))
            .thenReturn(CARRIER_ID);
        when(uriInfoMock.getAbsolutePathBuilder())
            .thenReturn(UriBuilder.fromPath(URI_PATH));

        Response actualResponse = carrierProfileResource.createCarrier(
            CREATE_CARRIER_REQUEST,
            uriInfoMock
        );

        assertEquals(Response.Status.CREATED.getStatusCode(), actualResponse.getStatus());
        assertEquals(URI_PATH + CARRIER_ID, actualResponse.getLocation().toString());
    }

    @Test
    void getCarriersEmail_profileServiceGetCarriersEmailIsCalled() {
        carrierProfileResource.getCarriersEmail();

        verify(carrierProfileServiceMock).getCarriersEmail();
    }
}
