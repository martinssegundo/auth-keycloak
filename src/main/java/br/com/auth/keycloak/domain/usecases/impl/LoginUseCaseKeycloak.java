package br.com.auth.keycloak.domain.usecases.impl;

import br.com.auth.keycloak.clients.AuthenticationService;
import br.com.auth.keycloak.configs.ApplicationScopedCA;
import br.com.auth.keycloak.domain.entities.Authorization;
import br.com.auth.keycloak.domain.entities.Login;
import br.com.auth.keycloak.domain.usecases.LoginUseCase;
import br.com.auth.keycloak.mappers.AuthenticationMapper;
import io.smallrye.mutiny.Uni;

@ApplicationScopedCA
public class LoginUseCaseKeycloak implements LoginUseCase {



    private final AuthenticationService authenticationService;
    private final AuthenticationMapper authenticationMapper;

    public LoginUseCaseKeycloak(AuthenticationService authenticationService,
                                AuthenticationMapper authenticationMapper) {
        this.authenticationService = authenticationService;
        this.authenticationMapper = authenticationMapper;
    }

    @Override
    public Uni<Authorization> login(Login login) {
        return authenticationService.login(login.getUser(),login.getPassword())
                .map(authorisationDTO -> authenticationMapper.convertToAuthorization(authorisationDTO));
    }
}
