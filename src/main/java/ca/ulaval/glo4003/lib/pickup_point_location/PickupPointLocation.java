package ca.ulaval.glo4003.lib.pickup_point_location;

import ca.ulaval.glo4003.lib.pickup_point_location.exception.InvalidPickupPointLocationException;
import java.util.Arrays;

public enum PickupPointLocation {
    VACHON("VACHON"),
    PEPS("PEPS"),
    DESJARDINS("DESJARDINS"),
    VANDRY("VANDRY"),
    MYRAND("MYRAND"),
    PYRAMIDE("PYRAMIDE"),
    CASAULT("CASAULT"),
    PLACE_STE_FOY("PLACE_STE_FOY");

    private final String location;

    PickupPointLocation(String location) {
        this.location = location;
    }

    public static PickupPointLocation fromString(String location) {
        return Arrays
            .stream(PickupPointLocation.values())
            .filter(pickupPoint -> pickupPoint.toString().equalsIgnoreCase(location))
            .findFirst()
            .orElseThrow(() -> new InvalidPickupPointLocationException(location));
    }

    @Override
    public String toString() {
        return location;
    }
}
