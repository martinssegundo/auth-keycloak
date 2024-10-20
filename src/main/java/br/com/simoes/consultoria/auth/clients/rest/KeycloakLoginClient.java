package br.com.simoes.consultoria.auth.clients.rest;

import br.com.simoes.consultoria.auth.clients.dtos.AuthorisationClientDataDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "keycloak-login-api")
@Path("/token")
public interface KeycloakLoginClient {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Uni<AuthorisationClientDataDTO> login(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                          Form authenticationInfo);

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Uni<AuthorisationClientDataDTO> refreshToken(Form authenticationInfo);
}
