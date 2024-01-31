package ca.ulaval.glo4003.constant;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import io.github.cdimascio.dotenv.Dotenv;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public final class Constants {

    private Constants() {}

    public static final class ClockSetup {

        private static Clock clock = Clock.systemDefaultZone();

        public static Clock getClock() {
            return clock;
        }

        public static void setClock(LocalDate time) {
            ClockSetup.clock =
                java.time.Clock.fixed(
                    time
                        .atStartOfDay()
                        .toInstant(
                            ClockSetup.clock
                                .getZone()
                                .getRules()
                                .getOffset(time.atStartOfDay())
                        ),
                    ClockSetup.clock.getZone()
                );
        }

        public static void resetClock() {
            ClockSetup.clock = Clock.systemDefaultZone();
        }

        private ClockSetup() {}
    }

    public static final class Kitchen {

        public static final PickupPointLocation KITCHEN_PICKUP_POINT_LOCATION =
            PickupPointLocation.DESJARDINS;

        private Kitchen() {}
    }

    public static final class ShipmentOrigin {

        public static final PickupPointLocation SHIPMENT_PICKUP_POINT_ORIGIN =
            PickupPointLocation.DESJARDINS;
        public static final int SHIPMENT_MAX_RANGE_DAY = 30;

        private ShipmentOrigin() {}
    }

    public static final class Communication {

        public static final String SHIPMENT_CREATED_EMAIL_OBJECT =
            "Cargaison prête à être ramassée";

        private Communication() {}
    }

    public static final class Date {

        public static final LocalTime DAY_START_TIME = LocalTime.of(9, 0, 0);
        public static final String DATE_FORMAT = "yyyy-MM-dd";

        private Date() {}
    }

    public static final class Auth {

        public static final int EXPIRATION_DELAY_IN_MS = 3600000;
        public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
        public static final String BEARER_PREFIX = "Bearer";
        public static final String TOKEN_PAYLOAD_ROLE_FIELD_NAME = "role";
        public static final String JWT_TOKEN_SECRET = "JWT_TOKEN_SECRET";
        public static final String ADMIN_PATH =
            "src/main/java/ca/ulaval/glo4003/repul/auth/infra/credential/admin/admins.json";

        private Auth() {}
    }

    public static final class Email {

        public static final String AUTHENTICATION_ENABLE = "true";
        public static final String TLS_ENABLE = "true";
        public static final String HOST_NAME = "smtp-mail.outlook.com";
        public static final String PORT = "587";

        public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
        public static final String PASSWORD_FIELD_NAME = "EMAIL_PASSWORD";

        public static final String ADDRESS = Dotenv
            .configure()
            .load()
            .get(Constants.Email.EMAIL_ADDRESS);
        public static final String PASSWORD = Dotenv
            .configure()
            .load()
            .get(Email.PASSWORD_FIELD_NAME);

        private Email() {}
    }

    public static final class Validator {

        public static final String ULAVAL_EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@ulaval\\.ca$";
        public static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        public static final String IDUL_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d.*\\d).+";
        public static final int USER_ACCOUNT_MINIMAL_AGE = 17;

        private Validator() {}

        public static final class Bill {

            public static final String DETAIL_CUSTOMER_ID_FIELD = "buyerId";
            public static final String DETAIL_PRODUCT_ID_FIELD = "productId";
            public static final String DETAIL_MEAL_KIT_TYPE_FIELD = "mealKitType";
            public static final String DETAIL_DELIVERY_DATE_FIELD = "deliveryDate";

            private Bill() {}
        }
    }

    public static final class Payment {

        public static final String CCV_FORMAT = "^\\d{3}$";
        public static final String INVALID_CCV_MSG = "Credit card CCV is invalid";

        private Payment() {}
    }

    public static final class Path {

        public static final String SEMESTER_CODE_REGEX = "^([AHE])\\d{2}$";
        public static final String JSON_SEMESTERS =
            "src/main/java/ca/ulaval/glo4003/lib/semester/registry/semesters.json";

        private Path() {}
    }

    public static final class HtmlTag {

        public static final String START_HEAD = "<head>";
        public static final String START_BODY = "<body>";
        public static final String START_HTML = "<html>";
        public static final String START_TABLE = "<table>";
        public static final String START_TABLE_BODY = "<tbody>";
        public static final String START_TABLE_ROW = "<tr>";
        public static final String START_TABLE_HEAD = "<thead>";
        public static final String END_HEAD = "</head>";
        public static final String END_BODY = "</body>";
        public static final String END_HTML = "</html>";
        public static final String END_TABLE = "</table>";
        public static final String END_TABLE_BODY = "</tbody>";
        public static final String END_TABLE_ROW = "</tr>";
        public static final String END_TABLE_HEAD = "</thead>";

        private HtmlTag() {}
    }

    public static final class Subscription {

        public static final int MEAL_KIT_PREPARATION_DELAY_IN_DAYS = 2;
        public static final int SPORADIC_CONFIRMATION_DELAY_IN_DAYS = 0;

        private Subscription() {}
    }
}
