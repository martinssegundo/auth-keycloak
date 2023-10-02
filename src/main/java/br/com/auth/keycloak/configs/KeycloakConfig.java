package br.com.auth.keycloak.configs;

import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "keycloak")
@Data
public class KeycloakConfig {

    private String user;
    private String password;
}
