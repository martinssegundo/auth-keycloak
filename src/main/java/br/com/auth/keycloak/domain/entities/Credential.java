package br.com.auth.keycloak.domain.entities;

import br.com.auth.keycloak.domain.entities.builds.CredentialBuilder;

public class Credential {
    private String type;
    private String value;
    private boolean temporary;

    public static CredentialBuilder builder() {
        return CredentialBuilder.getInstance();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }
}
