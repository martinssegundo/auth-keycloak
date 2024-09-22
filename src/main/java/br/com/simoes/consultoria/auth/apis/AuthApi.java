package br.com.simoes.consultoria.auth.apis;


import br.com.simoes.consultoria.auth.apis.dtos.LoginDataDTO;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.LoginUseCase;
import br.com.simoes.consultoria.auth.mappers.AuthenticationMapper;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthApi {

    private LoginUseCase loginUseCase;
    private AuthenticationMapper authenticationMapper;

    @Inject
    public AuthApi(@QualifierCA("loginUseCaseKeycloak") LoginUseCase loginUseCase,
                   AuthenticationMapper authenticationMapper) {
        this.loginUseCase = loginUseCase;
        this.authenticationMapper = authenticationMapper;
    }

    @POST
    @Path("/login")
    public Uni<Response> login(LoginDataDTO loginDataDTO) {
        return loginUseCase
                .login(
                        authenticationMapper.convertToLogin(loginDataDTO)
                )
                .map(auth ->
                        Response.ok(
                                authenticationMapper.convertToAuthorisationClientDataDTO(auth)
                        ).build()
                );
    }
}
