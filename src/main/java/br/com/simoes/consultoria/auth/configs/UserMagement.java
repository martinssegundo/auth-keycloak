package br.com.simoes.consultoria.auth.configs;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "keycloak.admin.users")
public interface UserMagement {
    @WithName("user")
    String user();
    @WithName("password")
    String password();
}
