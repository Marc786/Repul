package ca.ulaval.glo4003.repul.auth.infra.token;

import ca.ulaval.glo4003.constant.Constants;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidTokenException;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenPayload;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;

public class JWTAuth implements TokenAuth {

    @Override
    public TokenPayload decode(String token) {
        SecretKey secretKey = getSecretKey();

        try {
            Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

            Role role = Role.fromString(
                (String) claims.get(Constants.Auth.TOKEN_PAYLOAD_ROLE_FIELD_NAME)
            );
            Date expirationDate = claims.getExpiration();
            String id = claims.getSubject();

            if (role == null || expirationDate == null || id == null) {
                throw new InvalidTokenException();
            }

            return new TokenPayload(id, expirationDate, role);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    @Override
    public String generateToken(
        CredentialId credentialId,
        int expirationDelayInMs,
        Role role
    ) {
        return generateToken(credentialId.toString(), role);
    }

    private String generateToken(String id, Role role) {
        SecretKey secretKey = getSecretKey();

        return Jwts
            .builder()
            .setSubject(id)
            .setExpiration(
                new Date(
                    System.currentTimeMillis() + Constants.Auth.EXPIRATION_DELAY_IN_MS
                )
            )
            .claim(Constants.Auth.TOKEN_PAYLOAD_ROLE_FIELD_NAME, role.toString())
            .signWith(secretKey)
            .compact();
    }

    private static SecretKey getSecretKey() {
        byte[] secretKeyBytes = Objects
            .requireNonNull(
                Dotenv.configure().load().get(Constants.Auth.JWT_TOKEN_SECRET)
            )
            .getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(secretKeyBytes);
    }
}
