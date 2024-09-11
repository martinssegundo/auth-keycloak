package br.com.auth.keycloak.domain.entities.builds;

import br.com.auth.keycloak.clients.dtos.CredentialDTO;
import br.com.auth.keycloak.domain.entities.Credential;
import br.com.auth.keycloak.domain.entities.User;

import java.util.List;

public final class UserBuilder {
    private String username;
    private boolean enabled;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private List<Credential> credentials;

    private UserBuilder() {
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public UserBuilder emailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder credentials(List<Credential> credentials) {
        this.credentials = credentials;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUsername(username);
        user.setEnabled(enabled);
        user.setEmailVerified(emailVerified);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setCredentials(credentials);
        return user;
    }
}
