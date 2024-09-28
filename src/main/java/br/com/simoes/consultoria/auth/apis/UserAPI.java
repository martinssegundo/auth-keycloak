package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.dtos.UserCreateDTO;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.CreateUserUseCase;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserAPI {

    private final CreateUserUseCase createUserUseCase;
    private final UserMapper userMapper;

    @Inject
    public UserAPI(@QualifierCA("createUserUseCaseKeycloak") final CreateUserUseCase createUserUseCase,
                   final UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.userMapper = userMapper;
    }

    public Uni<Response> createUser(UserCreateDTO dto){
        return createUserUseCase.createUser(userMapper.converToUser(dto))
                .map(item -> Response.status(201).build());
    }
}
