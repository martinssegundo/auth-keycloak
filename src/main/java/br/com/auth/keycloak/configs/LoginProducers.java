package br.com.auth.keycloak.configs;

import br.com.auth.keycloak.clients.AuthenticationService;
import br.com.auth.keycloak.clients.rest.KeycloakClient;
import br.com.auth.keycloak.domain.usecases.impl.LoginUseCaseKeycloak;
import br.com.auth.keycloak.mappers.AuthenticationMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class LoginProducers {


    @Produces
    @ApplicationScoped
    @Named("loginUseCaseKeycloak")
    public LoginUseCaseKeycloak producesLoginUseCaseKeycloak(AuthenticationService authenticationService,
                                                             AuthenticationMapper authenticationMapper){
        return new LoginUseCaseKeycloak(authenticationService, authenticationMapper);
    }
}
