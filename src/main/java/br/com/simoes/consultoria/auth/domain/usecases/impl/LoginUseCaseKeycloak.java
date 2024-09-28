package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.AuthenticationService;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.entities.Authorization;
import br.com.simoes.consultoria.auth.domain.entities.Login;
import br.com.simoes.consultoria.auth.domain.usecases.LoginUseCase;
import br.com.simoes.consultoria.auth.mappers.AuthenticationMapper;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

@QualifierCA("loginUseCaseKeycloak")
@Slf4j
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
                .invoke(() -> log.info(
                        "LoginUseCaseKeycloak.login() Start keycloak login process from {}",
                        login.getUser()
                        ))
                .map(authenticationMapper::convertToAuthorization);
    }
}
