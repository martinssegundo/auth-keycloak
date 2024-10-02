package br.com.simoes.consultoria.auth.configs;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "quarkus.keycloak.admin-client")
public interface KeycloakConfig {

    String realm();
    @WithName("client-id")
    String clientId();
    @WithName("client-secret")
    String clientSecret();
    @WithName("url")
    String url();
    @WithName("admin-user")
    String user();
    @WithName("admin-password")
    String password();
}
