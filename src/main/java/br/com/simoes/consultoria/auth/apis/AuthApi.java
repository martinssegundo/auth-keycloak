package br.com.simoes.consultoria.auth.apis;


import br.com.simoes.consultoria.auth.apis.dtos.LoginDataDTO;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.LoginUseCase;
import br.com.simoes.consultoria.auth.mappers.AuthenticationMapper;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@Path("/auth")
@SecurityScheme(
        securitySchemeName = "auth-quarkus",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8090/realms/construction/protocol/openid-connect/token"))
)
@Slf4j
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
    @PermitAll
    public Uni<Response> login(LoginDataDTO loginDataDTO) {
        log.info("Start login from =====> {}", loginDataDTO.user());
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

    @GET
    @Authenticated
    public String getBlau(){
        return "String";
    }
}
