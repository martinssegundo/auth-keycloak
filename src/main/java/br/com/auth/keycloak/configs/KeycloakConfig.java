package br.com.auth.keycloak.configs;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "quarkus.keycloak.admin-client")
public interface KeycloakConfig {

    String url();
    String realm();
    @WithName("client-id")
    String clientId();
    @WithName("client-secret")
    String clientSecret();
}
