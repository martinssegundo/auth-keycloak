package br.com.simoes.consultoria.auth.clients.rest;


import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "keycloak-user-api")
@Path("/users")
public interface KeycloakUserClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Response> createNewUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader, UserDTO userDTO);

    @PUT
    @Path("/{user-id}/execute-actions-email")
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Response> triggerUserActions(@PathParam("user-id") String userId,
                                @HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                String[] actions);
}
