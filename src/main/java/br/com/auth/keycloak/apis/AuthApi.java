package br.com.auth.keycloak.apis;


import br.com.auth.keycloak.apis.dtos.LoginDataDTO;
import br.com.auth.keycloak.apis.dtos.UserAuthDTO;
import br.com.auth.keycloak.domain.usecases.LoginUseCase;
import br.com.auth.keycloak.mappers.AuthenticationMapper;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthApi {

    private LoginUseCase loginUseCase;
    private AuthenticationMapper authenticationMapper;

    @Inject
    public AuthApi(@Named("loginUseCaseKeycloak") LoginUseCase loginUseCase,
                   AuthenticationMapper authenticationMapper) {
        this.loginUseCase = loginUseCase;
        this.authenticationMapper = authenticationMapper;
    }

    @POST
    @Path("/login")
    public Uni<Response> login(LoginDataDTO loginDataDTO) {
        return loginUseCase.login(authenticationMapper.convertToLogin(loginDataDTO))
                .map(auth ->
                        Response.ok(
                            authenticationMapper.convertToAuthorisationClientDataDTO(auth)
                        ).build()
                );
    }
}
