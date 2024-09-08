package br.com.auth.keycloak.configs;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ApplicationScoped
@ConfigProperties(prefix = "keycloak")
@Data
public class KeycloakConfig {

    private String user;
    private String password;
}
