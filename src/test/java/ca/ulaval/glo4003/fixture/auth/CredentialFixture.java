package ca.ulaval.glo4003.fixture.auth;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.infra.credential.admin.AdminId;
import java.util.UUID;

public class CredentialFixture {

    private EmailAddress email = new EmailAddress("email");
    private Password password = new Password("password");

    public Credential buildAdminCredential() {
        AdminId adminId = new AdminId(UUID.randomUUID().toString());
        CredentialId credentialId = new CredentialId(adminId);
        Role role = Role.ADMIN;

        return new Credential(credentialId, email, password, role);
    }

    public Credential buildCustomerCredential() {
        CustomerProfileId customerProfileId = new CustomerProfileId(
            UUID.randomUUID().toString()
        );
        CredentialId credentialId = new CredentialId(customerProfileId);
        Role role = Role.CUSTOMER;

        return new Credential(credentialId, email, password, role);
    }

    public CredentialFixture withEmail(EmailAddress email) {
        this.email = email;
        return this;
    }

    public CredentialFixture withPassword(Password password) {
        this.password = password;
        return this;
    }
}
