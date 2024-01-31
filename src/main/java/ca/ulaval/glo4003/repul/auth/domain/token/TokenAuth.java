package ca.ulaval.glo4003.repul.auth.domain.token;

import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;

public interface TokenAuth {
    TokenPayload decode(String token);

    String generateToken(CredentialId id, int expirationDelayInMs, Role role);
}
