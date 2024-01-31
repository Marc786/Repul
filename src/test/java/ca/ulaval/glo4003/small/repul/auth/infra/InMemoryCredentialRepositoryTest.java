package ca.ulaval.glo4003.small.repul.auth.infra;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.fixture.auth.CredentialFixture;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.repul.auth.infra.credential.InMemoryCredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryCredentialRepositoryTest {

    private static final EmailAddress EMAIL = new EmailAddress("email");
    private static final Password PASSWORD = new Password("password");
    private static final EmailAddress ADMIN_EMAIL = new EmailAddress("admin@localhost");
    private final CredentialFixture credentialFixture = new CredentialFixture();
    private InMemoryCredentialRepository inMemoryCredentialRepository;

    @BeforeEach
    void setup() {
        inMemoryCredentialRepository = new InMemoryCredentialRepository();
    }

    @Test
    void savedCredential_findAll_deepCopyCredentialIsReturned() {
        Credential savedCredentials = credentialFixture
            .withEmail(EMAIL)
            .withPassword(PASSWORD)
            .buildAdminCredential();
        inMemoryCredentialRepository.save(savedCredentials);

        Credential credentialFound = inMemoryCredentialRepository.findUserCredential(
            savedCredentials.getEmail(),
            savedCredentials.getRole()
        );

        assertNotSame(savedCredentials, credentialFound);
        assertEquals(
            savedCredentials.getId().toString(),
            credentialFound.getId().toString()
        );
    }

    @Test
    void credentialNotSaved_findUserCredential_throwsException() {
        EmailAddress nonexistingEmail = new EmailAddress("nonexistingEmail");

        assertThrows(
            InvalidCredentialsException.class,
            () ->
                inMemoryCredentialRepository.findUserCredential(
                    nonexistingEmail,
                    Role.CUSTOMER
                )
        );
    }

    @Test
    void savedCredential_findAll_returnsSavedCredential() {
        Credential savedCredentials = credentialFixture
            .withEmail(EMAIL)
            .withPassword(PASSWORD)
            .buildAdminCredential();
        inMemoryCredentialRepository.save(savedCredentials);

        Credential credentialFound = inMemoryCredentialRepository.findUserCredential(
            savedCredentials.getEmail(),
            savedCredentials.getRole()
        );

        assertEquals(savedCredentials.getRole(), credentialFound.getRole());
        assertEquals(savedCredentials.getEmail(), credentialFound.getEmail());
        assertEquals(savedCredentials.getPassword(), credentialFound.getPassword());
    }

    @Test
    void initializeRepository_adminsCredentialsArePresent() {
        Role expectedRole = Role.ADMIN;

        InMemoryCredentialRepository initializedInMemoryCredentialRepository =
            new InMemoryCredentialRepository();

        Credential adminCredential =
            initializedInMemoryCredentialRepository.findUserCredential(
                ADMIN_EMAIL,
                Role.ADMIN
            );
        Role actualRole = adminCredential.getRole();
        assertEquals(expectedRole, actualRole);
    }
}
