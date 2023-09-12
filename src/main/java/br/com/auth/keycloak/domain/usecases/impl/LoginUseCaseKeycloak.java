package br.com.auth.keycloak.domain.usecases.impl;

import br.com.auth.keycloak.clients.KeycloakClient;
import br.com.auth.keycloak.domain.entities.Authorization;
import br.com.auth.keycloak.domain.entities.Login;
import br.com.auth.keycloak.domain.usecases.LoginUseCase;
import br.com.auth.keycloak.domain.util.AuthenticationUtil;
import br.com.auth.keycloak.mappers.AuthenticationMapper;
import io.smallrye.mutiny.Uni;

public class LoginUseCaseKeycloak implements LoginUseCase {

    private static final String GRANT_TYPE = "password";

    private final KeycloakClient keycloakClient;
    private final AuthenticationMapper authenticationMapper;

    public LoginUseCaseKeycloak(KeycloakClient keycloakClient,
                                AuthenticationMapper authenticationMapper) {
        this.keycloakClient = keycloakClient;
        this.authenticationMapper = authenticationMapper;
    }

    @Override
    public Uni<Authorization> login(Login login) {
        return keycloakClient.login(
                AuthenticationUtil.buildBasic("user", "password"),
                AuthenticationUtil.buildForm(GRANT_TYPE, "user", "password")
        ).map(authorisationDTO -> authenticationMapper.convertToAuthorization(authorisationDTO));

    }
}
