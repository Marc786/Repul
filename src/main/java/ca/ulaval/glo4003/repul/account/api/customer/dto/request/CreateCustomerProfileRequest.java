package ca.ulaval.glo4003.repul.account.api.customer.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateCustomerProfileRequest(
    @NotBlank String email,
    @NotBlank String password,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String birthDate,
    @NotBlank String gender,
    @NotBlank String customerId,
    @NotBlank String studentCardNumber
) {}
