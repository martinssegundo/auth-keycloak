package br.com.auth.keycloak.domain.usecases.impl;

import br.com.auth.keycloak.clients.AuthenticationService;
import br.com.auth.keycloak.domain.entities.User;
import br.com.auth.keycloak.domain.usecases.CreateUserUseCase;
import br.com.auth.keycloak.mappers.UserMapper;
import io.smallrye.mutiny.Uni;

public class CreateUserUseCaseKeycloak implements CreateUserUseCase {
    private AuthenticationService authenticationService;
    private UserMapper userMapper;

    public CreateUserUseCaseKeycloak(AuthenticationService authenticationService,
                                     UserMapper userMapper){
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @Override
    public Uni<Void> createUser(User user) {
        return authenticationService.createUser(userMapper.converToUserDTO(user));
    }
}
