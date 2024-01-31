package ca.ulaval.glo4003.repul.shipment.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AssembledShipmentItemsRequest(
    @NotNull List<AssembledShipmentItemRequest> shipmentItemsIds
) {}
