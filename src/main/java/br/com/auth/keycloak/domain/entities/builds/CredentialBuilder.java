package br.com.auth.keycloak.domain.entities.builds;

import br.com.auth.keycloak.domain.entities.Credential;

public final class CredentialBuilder {
    private String type;
    private String value;
    private boolean temporary;

    private CredentialBuilder() {
    }

    public static CredentialBuilder getInstance() {
        return new CredentialBuilder();
    }

    public CredentialBuilder type(String type) {
        this.type = type;
        return this;
    }

    public CredentialBuilder value(String value) {
        this.value = value;
        return this;
    }

    public CredentialBuilder temporary(boolean temporary) {
        this.temporary = temporary;
        return this;
    }

    public Credential build() {
        Credential credential = new Credential();
        credential.setType(type);
        credential.setValue(value);
        credential.setTemporary(temporary);
        return credential;
    }
}
