package br.com.auth.keycloak.clients;

import br.com.auth.keycloak.clients.dtos.AuthorisationDataDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "keycloak-api")
public interface KeycloakClient {


    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Uni<AuthorisationDataDTO> login(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader, Form authenticationInfo);
}
