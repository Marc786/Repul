package ca.ulaval.glo4003.repul.communication.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShipmentInfoRequest(
    @NotBlank String shipmentId,
    @NotBlank String pickUpPointLocation,
    @NotNull Map<String, String> shipmentItemsAndLocation
) {}
