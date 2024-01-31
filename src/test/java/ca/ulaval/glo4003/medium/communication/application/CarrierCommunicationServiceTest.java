package ca.ulaval.glo4003.medium.communication.application;

import static ca.ulaval.glo4003.repul.communication.infra.ShipmentEmailFormatter.toEmailResponse;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.communication.application.CarrierCommunicationService;
import ca.ulaval.glo4003.repul.communication.domain.CarrierProfileClient;
import ca.ulaval.glo4003.repul.communication.domain.ShipmentInfo;
import ca.ulaval.glo4003.repul.communication.infra.CarrierMailClient;
import ca.ulaval.glo4003.repul.communication.infra.ShipmentEmailFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class CarrierCommunicationServiceTest {

    private static final String MEAL_KIT_SHIPMENT_EMAIL_CONTENT = "foo";
    private static final EmailAddress EMAIL_ADDRESS = new EmailAddress("foo@gmail.com");
    private static final List<EmailAddress> EMAIL_ADDRESSES = List.of(EMAIL_ADDRESS);

    private final MockedStatic<ShipmentEmailFormatter> shipmentEmailFormatterMock =
        mockStatic(ShipmentEmailFormatter.class);
    private final CarrierMailClient mailClientMock = Mockito.mock(
        CarrierMailClient.class
    );
    private final CarrierProfileClient carrierProfileClientMock = Mockito.mock(
        CarrierProfileClient.class
    );

    private final CarrierCommunicationService carrierCommunicationService =
        new CarrierCommunicationService(mailClientMock, carrierProfileClientMock);

    private final ShipmentInfo shipmentInfoMock = Mockito.mock(ShipmentInfo.class);

    @Test
    void sendEmailToShipmentCarriers_sendEmailsIsCalled() {
        int expectedCalledTimes = EMAIL_ADDRESSES.size();
        when(carrierProfileClientMock.getCarriersEmail()).thenReturn(EMAIL_ADDRESSES);
        shipmentEmailFormatterMock
            .when(() -> toEmailResponse(shipmentInfoMock))
            .thenReturn(MEAL_KIT_SHIPMENT_EMAIL_CONTENT);

        carrierCommunicationService.sendEmailToShipmentCarriers(shipmentInfoMock);

        verify(mailClientMock, times(expectedCalledTimes))
            .notifyCarriersShipmentCreated(eq(shipmentInfoMock), eq(EMAIL_ADDRESSES));
    }
}
