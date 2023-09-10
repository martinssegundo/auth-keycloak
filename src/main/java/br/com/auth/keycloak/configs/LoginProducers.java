package br.com.auth.keycloak.configs;

import br.com.auth.keycloak.clients.KeycloakClient;
import br.com.auth.keycloak.domain.usecases.impl.LoginUseCaseKeycloak;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

@ApplicationScoped
public class LoginProducers {

    @Produces
    public LoginUseCaseKeycloak producesLoginUseCaseKeycloak(KeycloakClient keycloakClient){
        return new LoginUseCaseKeycloak(keycloakClient);
    }
}
