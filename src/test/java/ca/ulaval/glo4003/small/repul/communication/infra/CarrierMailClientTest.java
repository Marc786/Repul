package ca.ulaval.glo4003.small.repul.communication.infra;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.communication.domain.ShipmentInfo;
import ca.ulaval.glo4003.repul.communication.domain.exception.UnableToSendEmailException;
import ca.ulaval.glo4003.repul.communication.infra.CarrierMailClient;
import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class CarrierMailClientTest {

    private static final EmailAddress EMAIL_ADDRESS = new EmailAddress("test@abc.com");
    private final ShipmentInfo shipmentInfo = mock(ShipmentInfo.class);
    private CarrierMailClient mailClient;

    @BeforeEach
    void setUp() {
        mailClient = CarrierMailClient.getInstance();
    }

    @Test
    void notifyCarriersShipmentCreated_theEmailIsSent() {
        try (
            MockedStatic<Transport> transportMockedStatic = Mockito.mockStatic(
                Transport.class
            )
        ) {
            mailClient.notifyCarriersShipmentCreated(
                shipmentInfo,
                List.of(EMAIL_ADDRESS)
            );

            transportMockedStatic.verify(() ->
                Transport.send(ArgumentMatchers.any(Message.class))
            );
        }
    }

    @Test
    void messagingException_notifyCarriersShipmentCreated_throwsUnableToSendEmailException() {
        try (
            MockedStatic<Transport> transportMockedStatic = Mockito.mockStatic(
                Transport.class
            )
        ) {
            transportMockedStatic
                .when(() -> Transport.send(ArgumentMatchers.any(Message.class)))
                .thenThrow(new MessagingException());

            assertThrows(
                UnableToSendEmailException.class,
                () ->
                    mailClient.notifyCarriersShipmentCreated(
                        shipmentInfo,
                        List.of(EMAIL_ADDRESS)
                    )
            );
        }
    }
}
