package br.com.auth.keycloak.clients.rest;


import br.com.auth.keycloak.clients.dtos.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "keycloak-user-api")
@Path("/users")
public interface KeycloakUserClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Void> createNewUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader, UserDTO userDTO);
}
