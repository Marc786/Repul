package ca.ulaval.glo4003.repul.subscription.api.dto.response;

public record MealKitResponse(
    String id,
    String type,
    String deliveryDate,
    String pickupPointLocation,
    String status
) {}
