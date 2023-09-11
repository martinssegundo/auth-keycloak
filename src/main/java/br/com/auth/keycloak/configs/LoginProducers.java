package br.com.auth.keycloak.configs;

import br.com.auth.keycloak.clients.KeycloakClient;
import br.com.auth.keycloak.domain.usecases.impl.LoginUseCaseKeycloak;
import br.com.auth.keycloak.mappers.AuthenticationMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class LoginProducers {

    private KeycloakClient keycloakClient;
    private AuthenticationMapper authenticationMapper;

    @Inject
    public LoginProducers(@RestClient KeycloakClient keycloakClient,
                          AuthenticationMapper authenticationMapper) {
        this.keycloakClient = keycloakClient;
        this.authenticationMapper = authenticationMapper;
    }

    @Produces
    @ApplicationScoped
    @Named("loginUseCaseKeycloak")
    public LoginUseCaseKeycloak producesLoginUseCaseKeycloak(){
        return new LoginUseCaseKeycloak(keycloakClient, authenticationMapper);
    }
}
