package ca.ulaval.glo4003.repul.communication.infra;

import static ca.ulaval.glo4003.constant.Constants.Communication.SHIPMENT_CREATED_EMAIL_OBJECT;

import ca.ulaval.glo4003.constant.Constants;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.communication.domain.CarrierCommunicationClient;
import ca.ulaval.glo4003.repul.communication.domain.ShipmentInfo;
import ca.ulaval.glo4003.repul.communication.domain.exception.UnableToSendEmailException;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class CarrierMailClient
    extends Authenticator
    implements CarrierCommunicationClient {

    private static final String PROPERTIES_SMTP_AUTH_KEY = "mail.smtp.auth";
    private static final String PROPERTIES_SMTP_START_TLS_KEY =
        "mail.smtp.starttls.enable";
    private static final String PROPERTIES_SMTP_HOST_KEY = "mail.smtp.host";
    private static final String PROPERTIES_SMTP_PORT_KEY = "mail.smtp.port";
    private static CarrierMailClient instance;
    private final Session session;

    private CarrierMailClient() {
        Properties properties = createSmtpProperties();

        session = Session.getInstance(properties, this);
    }

    public static CarrierMailClient getInstance() {
        if (instance == null) {
            instance = new CarrierMailClient();
        }
        return instance;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(
            Constants.Email.ADDRESS,
            Constants.Email.PASSWORD
        );
    }

    public void notifyCarriersShipmentCreated(
        ShipmentInfo shipmentInfo,
        List<EmailAddress> emailAddresses
    ) {
        emailAddresses.forEach(email -> notifyCarrierShipmentCreated(shipmentInfo, email)
        );
    }

    private void notifyCarrierShipmentCreated(
        ShipmentInfo shipmentInfo,
        EmailAddress emailAddress
    ) {
        try {
            String content = ShipmentEmailFormatter.toEmailResponse(shipmentInfo);
            Message message = createMessage(
                SHIPMENT_CREATED_EMAIL_OBJECT,
                content,
                emailAddress.toString()
            );

            Transport.send(message);
        } catch (MessagingException e) {
            throw new UnableToSendEmailException();
        }
    }

    private Properties createSmtpProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTIES_SMTP_AUTH_KEY, Constants.Email.AUTHENTICATION_ENABLE);
        properties.put(PROPERTIES_SMTP_START_TLS_KEY, Constants.Email.TLS_ENABLE);
        properties.put(PROPERTIES_SMTP_HOST_KEY, Constants.Email.HOST_NAME);
        properties.put(PROPERTIES_SMTP_PORT_KEY, Constants.Email.PORT);
        return properties;
    }

    private Message createMessage(String object, String content, String emailAddress)
        throws MessagingException {
        Message message = new MimeMessage(session);
        InternetAddress internetAddress = new InternetAddress(Constants.Email.ADDRESS);

        message.setFrom(internetAddress);
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(emailAddress)
        );
        message.setSubject(object);
        message.setContent(content, "text/html");

        return message;
    }
}
