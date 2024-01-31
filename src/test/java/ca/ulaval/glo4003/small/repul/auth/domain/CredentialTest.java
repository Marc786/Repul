package ca.ulaval.glo4003.small.repul.auth.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CredentialTest {

    private static final CredentialId ID = new CredentialId("id");
    private static final EmailAddress EMAIL = new EmailAddress("email");
    private static final Password PASSWORD = new Password("password");
    private static final Role ROLE = Role.CUSTOMER;
    private static final Password INVALID_PASSWORD = new Password("invalidPassword");

    private Credential credential;

    @BeforeEach
    void setup() {
        credential = new Credential(ID, EMAIL, PASSWORD, ROLE);
    }

    @Test
    void validPassword_verifyPassword_doesNotThrow() {
        assertDoesNotThrow(() -> credential.verifyPassword(PASSWORD));
    }

    @Test
    void invalidPassword_verifyPassword_throwsInvalidCredentialsException() {
        assertThrows(
            InvalidCredentialsException.class,
            () -> credential.verifyPassword(INVALID_PASSWORD)
        );
    }
}
