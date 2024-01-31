package ca.ulaval.glo4003.repul.auth.api.dto.request;

import static ca.ulaval.glo4003.constant.Constants.Validator.EMAIL_REGEX;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(
    @Email(regexp = EMAIL_REGEX) @NotBlank String email,
    @NotBlank String password,
    @NotBlank String role
) {}
