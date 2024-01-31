package ca.ulaval.glo4003.repul.account.api.customer.dto.response;

public record CustomerProfileResponse(
    String firstName,
    String lastName,
    String birthDate,
    String gender,
    int age,
    String idul
) {}
