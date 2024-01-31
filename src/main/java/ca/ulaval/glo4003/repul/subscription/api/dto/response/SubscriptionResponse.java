package ca.ulaval.glo4003.repul.subscription.api.dto.response;

public record SubscriptionResponse(
    String id,
    String frequency,
    String pickupPointLocation,
    String mealKitType,
    String startDate,
    String endDate
) {}
