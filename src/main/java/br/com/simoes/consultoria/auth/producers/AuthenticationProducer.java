package br.com.simoes.consultoria.auth.producers;

import br.com.simoes.consultoria.auth.clients.AuthenticationService;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.impl.LoginUseCaseKeycloak;
import br.com.simoes.consultoria.auth.domain.usecases.impl.RefreshTokenUseCaseKeycloak;
import br.com.simoes.consultoria.auth.mappers.AuthenticationMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class AuthenticationProducer {


    @Produces
    @QualifierCA("loginUseCaseKeycloak")
    public LoginUseCaseKeycloak producesLoginUseCaseKeycloak(AuthenticationService authenticationService,
                                                             AuthenticationMapper authenticationMapper) {
        return new LoginUseCaseKeycloak(authenticationService, authenticationMapper);
    }


    @Produces
    @QualifierCA("refreshTokenUseCaseKeycloak")
    public RefreshTokenUseCaseKeycloak producesRefreshTokenUseCaseKeycloak(AuthenticationService authenticationService,
                                                                    AuthenticationMapper authenticationMapper) {
        return new RefreshTokenUseCaseKeycloak(authenticationService, authenticationMapper);
    }
}
