package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.domain.entities.User;
import br.com.simoes.consultoria.auth.domain.usecases.FindUserUseCase;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.smallrye.mutiny.Uni;

import java.util.List;

public class FindUserUseCaseKeyCloak implements FindUserUseCase {

    private final UserManagerService createUserService;
    private final UserMapper userMapper;

    public FindUserUseCaseKeyCloak(final UserManagerService createUserService,
                                   final UserMapper userMapper) {
        this.createUserService = createUserService;
        this.userMapper = userMapper;
    }

    @Override
    public Uni<List<User>> findUser(String username, Integer max, Integer page) {
        return createUserService.findUser(username,max,page)
                .map(userMapper::convertDtoListToEntityList);
    }
}
