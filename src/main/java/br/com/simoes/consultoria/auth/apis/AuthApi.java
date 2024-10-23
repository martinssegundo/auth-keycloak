package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.dtos.request.LoginDataDTO;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.LoginUseCase;
import br.com.simoes.consultoria.auth.domain.usecases.RefreshTokenUseCase;
import br.com.simoes.consultoria.auth.mappers.AuthenticationMapper;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/auth")
@Slf4j
@Tag(name = "Auth API", description = "API para autenticação e gerenciamento de tokens.")
public class AuthApi {

    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final AuthenticationMapper authenticationMapper;

    @Inject
    public AuthApi(@QualifierCA("loginUseCaseKeycloak") final LoginUseCase loginUseCase,
                   @QualifierCA("refreshTokenUseCaseKeycloak") final RefreshTokenUseCase refreshTokenUseCase,
                   final AuthenticationMapper authenticationMapper) {
        this.loginUseCase = loginUseCase;
        this.authenticationMapper = authenticationMapper;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    @POST
    @Path("/login")
    @PermitAll
    @Operation(
            summary = "Realiza login",
            description = "Autentica um usuário com base nas credenciais fornecidas."
    )
    @APIResponse(
            responseCode = "200",
            description = "Login realizado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "401",
            description = "Falha na autenticação, credenciais inválidas"
    )
    @RequestBody(
            description = "Credenciais de login do usuário",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginDataDTO.class)
            )
    )
    public Uni<Response> login(LoginDataDTO loginDataDTO) {
        log.info("AuthApi.login() Start login process from =====> {}", loginDataDTO.user());
        return loginUseCase
                .login(
                        authenticationMapper.convertToLogin(loginDataDTO)
                )
                .invoke(() -> log.info("Login successful to user: {}", loginDataDTO.user()))
                .map(auth ->
                        Response.ok(
                                authenticationMapper.convertToAuthorisationClientDataDTO(auth)
                        ).build()
                );
    }

    @POST
    @Path("/refresh")
    @PermitAll
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Atualiza o token de acesso",
            description = "Renova o token de acesso com base no token de refresh fornecido."
    )
    @APIResponse(
            responseCode = "200",
            description = "Token renovado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Falha na renovação do token"
    )
    @RequestBody(
            description = "Token de refresh do usuário",
            required = true,
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(type = SchemaType.STRING, example = "eyJhbGciOiJIUzI1NiIsIn...")
            )
    )
    public Uni<Response> refreshToken(String refreshToken) {
        log.info("AuthApi.refreshToken() Start refresh token");
        return refreshTokenUseCase.refresh(refreshToken)
                .invoke(() -> log.info("Refresh token successful"))
                .map(auth ->
                        Response.ok(
                                authenticationMapper.convertToAuthorisationClientDataDTO(auth)
                        ).build()
                );
    }

}
