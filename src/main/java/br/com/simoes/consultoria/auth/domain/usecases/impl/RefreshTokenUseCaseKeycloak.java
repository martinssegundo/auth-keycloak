package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.AuthenticationService;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.entities.Authorization;
import br.com.simoes.consultoria.auth.domain.usecases.RefreshTokenUseCase;
import br.com.simoes.consultoria.auth.mappers.AuthenticationMapper;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;


@QualifierCA("refreshTokenUseCaseKeycloak")
public class RefreshTokenUseCaseKeycloak implements RefreshTokenUseCase {

    private final AuthenticationService authenticationService;
    private final AuthenticationMapper authenticationMapper;

    public RefreshTokenUseCaseKeycloak(final AuthenticationService authenticationService,
                                       final AuthenticationMapper authenticationMapper) {
        this.authenticationService = authenticationService;
        this.authenticationMapper = authenticationMapper;
    }

    @Override
    public Uni<Authorization> refresh(String refreshToken) {
        return authenticationService.refresh(refreshToken)
                .map(authenticationMapper::convertToAuthorization);
    }
}
