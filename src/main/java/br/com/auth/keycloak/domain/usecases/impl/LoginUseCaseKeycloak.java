package br.com.auth.keycloak.domain.usecases.impl;

import br.com.auth.keycloak.clients.KeycloakClient;
import br.com.auth.keycloak.domain.entities.Authorization;
import br.com.auth.keycloak.domain.entities.Login;
import br.com.auth.keycloak.domain.usecases.LoginUseCase;
import io.smallrye.mutiny.Uni;

public class LoginUseCaseKeycloak implements LoginUseCase {

    KeycloakClient keycloakClient;
    public LoginUseCaseKeycloak(KeycloakClient keycloakClient){
        this.keycloakClient = keycloakClient;
    }

    @Override
    public Uni<Authorization> login(Login login) {
        return null;
    }
}
