package ca.ulaval.glo4003.small.lib.pickup_point_location.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.lib.pickup_point_location.exception.InvalidPickupPointLocationException;
import org.junit.jupiter.api.Test;

class PickupPointLocationTest {

    private static final String VALID_STRING_LOCATION = "vachon";
    private static final String INVALID_LOCATION = "invalidLocation";

    @Test
    void givenValidLocation_whenFromString_thenReturnPickupPointLocation() {
        PickupPointLocation actualPickupPointLocation = PickupPointLocation.fromString(
            VALID_STRING_LOCATION
        );

        assertEquals(PickupPointLocation.VACHON, actualPickupPointLocation);
    }

    @Test
    void givenInvalidLocation_whenFromString_thenThrowInvalidPickupPointLocationException() {
        assertThrows(
            InvalidPickupPointLocationException.class,
            () -> PickupPointLocation.fromString(INVALID_LOCATION)
        );
    }
}
