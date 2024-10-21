package br.com.simoes.consultoria.auth.apis;


import br.com.simoes.consultoria.auth.apis.dtos.LoginDataDTO;
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

@Path("/auth")
@Slf4j
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
