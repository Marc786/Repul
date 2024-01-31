package ca.ulaval.glo4003.repul.shipment.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AssembledShipmentItemRequest(@NotBlank String shipmentItemId) {}
