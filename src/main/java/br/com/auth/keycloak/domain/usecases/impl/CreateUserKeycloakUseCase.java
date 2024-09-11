package br.com.auth.keycloak.domain.usecases.impl;

import br.com.auth.keycloak.clients.AuthenticationService;
import br.com.auth.keycloak.clients.UserManagerService;
import br.com.auth.keycloak.configs.QualifierCA;
import br.com.auth.keycloak.domain.entities.User;
import br.com.auth.keycloak.domain.usecases.CreateUserUseCase;
import br.com.auth.keycloak.mappers.UserMapper;
import io.smallrye.mutiny.Uni;

@QualifierCA("createUserUseCaseKeycloak")
public class CreateUserKeycloakUseCase implements CreateUserUseCase {
    private final UserManagerService createUserService;
    private final UserMapper userMapper;

    public CreateUserKeycloakUseCase(final UserManagerService createUserService,
                                     final UserMapper userMapper){
        this.createUserService = createUserService;
        this.userMapper = userMapper;
    }

    @Override
    public Uni<Void> createUser(User user) {
        return createUserService.createUser(userMapper.converToUserDTO(user));
    }
}
