package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.dtos.UserCreateDTO;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.CreateUserUseCase;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/user")
@Slf4j
public class UserAPI {

    private final CreateUserUseCase createUserUseCase;
    private final UserMapper userMapper;
    private final SecurityIdentity securityIdentity;
    private final JsonWebToken jwt;

    @Inject
    public UserAPI(@QualifierCA("createUserUseCaseKeycloak") final CreateUserUseCase createUserUseCase,
                   final UserMapper userMapper,
                   final SecurityIdentity securityIdentity,
                   final JsonWebToken jwt) {
        this.createUserUseCase = createUserUseCase;
        this.userMapper = userMapper;
        this.securityIdentity = securityIdentity;
        this.jwt = jwt;
    }

    @POST
    @Authenticated
    @Blocking
    public Uni<Response> createUser(UserCreateDTO dto){
        logRequestIdentifier(dto);
        return createUserUseCase.createUser(userMapper.converToUser(dto))
                .map(item -> Response.status(201).build());
    }


    private void logRequestIdentifier(UserCreateDTO dto){
        String username = securityIdentity.getPrincipal().getName();
        log.info("UserAPI.createUser() =======> " +
                "user: {} " +
                "start create new user: {}",
                username,
                dto.username());
    }
}
