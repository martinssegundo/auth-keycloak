package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.domain.usecases.DisableUserByUserIdUseCase;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.smallrye.mutiny.Uni;

public class DisableUserByUserIdUseCaseKeycloak implements DisableUserByUserIdUseCase {

    private final UserManagerService disableUserService;

    public DisableUserByUserIdUseCaseKeycloak(UserManagerService disableUserService) {
        this.disableUserService = disableUserService;
    }

    @Override
    public Uni<Void> disableUser(String userId) {
        return disableUserService.disableUserByUserId(userId);
    }
}
