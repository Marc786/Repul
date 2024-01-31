package ca.ulaval.glo4003.repul.auth.infra.credential;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.infra.credential.admin.AdminJson;
import java.util.List;

public class CredentialAssembler {

    public List<Credential> fromJson(List<AdminJson> adminJson) {
        return adminJson.stream().map(this::fromJson).toList();
    }

    private Credential fromJson(AdminJson adminJson) {
        return new Credential(
            new CredentialId(adminJson.id()),
            new EmailAddress(adminJson.email()),
            new Password(adminJson.password()),
            Role.valueOf(adminJson.role())
        );
    }
}
