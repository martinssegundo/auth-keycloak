package br.com.simoes.consultoria.auth.clients.rest;


import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "keycloak-user-api")
@Path("/users")
public interface KeycloakUserClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<List<UserDTO>> getUsersByFilter(@HeaderParam("Authorization") String authToken,
                                        @QueryParam("search") String username,
                                        @QueryParam("max") @DefaultValue("20") int max,
                                        @QueryParam("first") @DefaultValue("0") int page);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<List<UserDTO>> getAllUsers(@HeaderParam("Authorization") String authToken,
                                   @QueryParam("max") @DefaultValue("20") int max,
                                   @QueryParam("first") @DefaultValue("0") int page);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<UserDTO> updateUserByUserID(@HeaderParam("Authorization") String authToken,
                                  @PathParam("id") @DefaultValue("0") String userID,
                                  UserDTO user);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<UserDTO> findUserByUserID(@HeaderParam("Authorization") String authToken,
                                    @PathParam("id") @DefaultValue("0") String userID);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Response> createNewUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                UserDTO user);

    @PUT
    @Path("/{user-id}/execute-actions-email")
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Response> triggerUserActions(@PathParam("user-id") String userId,
                                     @HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                     String[] actions);
}
