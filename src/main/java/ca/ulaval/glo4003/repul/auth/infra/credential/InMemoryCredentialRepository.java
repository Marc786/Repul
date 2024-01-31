package ca.ulaval.glo4003.repul.auth.infra.credential;

import ca.ulaval.glo4003.constant.Constants;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialRepository;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.repul.auth.infra.credential.admin.AdminJson;
import ca.ulaval.glo4003.repul.auth.infra.credential.cloner.CredentialFastCloner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rits.cloning.Cloner;
import jakarta.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InMemoryCredentialRepository implements CredentialRepository {

    private final Cloner cloner = new Cloner();
    private final CredentialAssembler credentialAssembler = new CredentialAssembler();
    private final List<Credential> credentials = new ArrayList<>();

    public InMemoryCredentialRepository() {
        loadAdminCredentials();
        cloner.registerFastCloner(Credential.class, new CredentialFastCloner());
    }

    @Override
    public void save(Credential credential) {
        removeIfExisting(credential);
        this.credentials.add(credential);
    }

    @Override
    public Credential findUserCredential(EmailAddress email, Role role) {
        Credential credentialFound = credentials
            .stream()
            .filter(credential -> credential.getEmail().equals(email))
            .filter(credential -> credential.getRole().equals(role))
            .findFirst()
            .orElseThrow(InvalidCredentialsException::new);
        return cloner.deepClone(credentialFound);
    }

    private void loadAdminCredentials() {
        List<AdminJson> adminsJson = readAdminFromJsonFile();
        List<Credential> adminCredentials = credentialAssembler.fromJson(adminsJson);

        this.credentials.addAll(adminCredentials);
    }

    private List<AdminJson> readAdminFromJsonFile() {
        try {
            String json = Files.readString(
                Path.of(Constants.Auth.ADMIN_PATH),
                StandardCharsets.UTF_8
            );
            ObjectMapper objectMapper = new ObjectMapper();
            return List.of(objectMapper.readValue(json, AdminJson[].class));
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

    private void removeIfExisting(Credential credential) {
        credentials.removeIf(existingCredential ->
            existingCredential.getId().equals(credential.getId())
        );
    }
}
