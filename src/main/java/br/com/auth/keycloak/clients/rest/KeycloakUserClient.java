package br.com.auth.keycloak.clients.rest;


import br.com.auth.keycloak.clients.dtos.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "keycloak-user-api")
@Path("/users")
public interface KeycloakUserClient {

    @POST
    Uni<Void> createNewUser(UserDTO userDTO);
}
