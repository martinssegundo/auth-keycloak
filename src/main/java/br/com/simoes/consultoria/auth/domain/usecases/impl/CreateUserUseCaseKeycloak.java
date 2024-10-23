package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.entities.User;
import br.com.simoes.consultoria.auth.domain.usecases.CreateUserUseCase;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.smallrye.mutiny.Uni;

@QualifierCA("createUserUseCaseKeycloak")
public class CreateUserUseCaseKeycloak implements CreateUserUseCase {
    private final UserManagerService createUserService;
    private final UserMapper userMapper;

    public CreateUserUseCaseKeycloak(final UserManagerService createUserService,
                                     final UserMapper userMapper){
        this.createUserService = createUserService;
        this.userMapper = userMapper;
    }

    @Override
    public Uni<Void> createUser(User user) {
        return createUserService.createUser(userMapper.converToUserDTO(user));
    }
}
