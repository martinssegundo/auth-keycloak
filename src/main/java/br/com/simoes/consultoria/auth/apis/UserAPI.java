package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.dtos.request.UserCreateDTO;
import br.com.simoes.consultoria.auth.apis.dtos.request.UserCriteriaDTO;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.CreateUserUseCase;
import br.com.simoes.consultoria.auth.domain.usecases.FindUserByLoginOrNameOrEmailUseCase;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/user")
@Slf4j
@Tag(name = "User API", description = "API para gerenciamento de usuários")
public class UserAPI {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByLoginOrNameOrEmailUseCase findUserByLoginOrNameOrEmailUseCase;
    private final UserMapper userMapper;
    private final SecurityIdentity securityIdentity;
    private final JsonWebToken jwt;

    @Inject
    public UserAPI(@QualifierCA("createUserUseCaseKeycloak") final CreateUserUseCase createUserUseCase,
                   @QualifierCA("findUserUseCaseKeyCloak") final FindUserByLoginOrNameOrEmailUseCase findUserByLoginOrNameOrEmailUseCase,
                   final UserMapper userMapper,
                   final SecurityIdentity securityIdentity,
                   final JsonWebToken jwt) {
        this.createUserUseCase = createUserUseCase;
        this.userMapper = userMapper;
        this.securityIdentity = securityIdentity;
        this.findUserByLoginOrNameOrEmailUseCase = findUserByLoginOrNameOrEmailUseCase;
        this.jwt = jwt;
    }

    @GET
    @Path("/find")
    @RolesAllowed("manager")
    @Blocking
    @Operation(
            summary = "Busca usuároi por login, nome ou email",
            description = "Retorna uma lista de usuários filtrados com base nos critérios fornecidos"
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista de usuários encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCriteriaDTO.class))
    )
    @APIResponse(
            responseCode = "403",
            description = "Acesso negado"
    )
    public Uni<Response> findUserByNameOrFirstNameOrEmail(
            @RequestBody(description = "Critérios de pesquisa de usuários, login, primeiro nome ou email") UserCriteriaDTO userCriteria) {
        return findUserByLoginOrNameOrEmailUseCase.findUserByNameOrFirsNamOrEmail(userCriteria.search(), userCriteria.maxUser(), userCriteria.page())
                .map(userMapper::convertEntityListToResponseList)
                .map(listResponse -> Response.ok(listResponse).build());
    }

    @POST
    @RolesAllowed("manager")
    @Blocking
    @Operation(
            summary = "Criar um novo usuário",
            description = "Cria um novo usuário no sistema com base nos dados fornecidos"
    )
    @APIResponse(
            responseCode = "201",
            description = "Usuário criado com sucesso"
    )
    @APIResponse(
            responseCode = "400",
            description = "Erro na criação do usuário"
    )
    public Uni<Response> createUser(
            @RequestBody(description = "Dados do usuário a ser criado", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCreateDTO.class))) UserCreateDTO dto) {
        logRequestIdentifier(dto);
        return createUserUseCase.createUser(userMapper.converToUser(dto))
                .invoke(() -> log.info("User {} created", dto.username()))
                .map(item -> Response.status(201).build());
    }

    private void logRequestIdentifier(UserCreateDTO dto) {
        String username = securityIdentity.getPrincipal().getName();
        log.info("UserAPI.createUser() =======> " +
                        "user: {} " +
                        "start create new user: {}",
                username,
                dto.username());
    }
}
