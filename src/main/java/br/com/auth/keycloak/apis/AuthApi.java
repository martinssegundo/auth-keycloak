package br.com.auth.keycloak.apis;


import br.com.auth.keycloak.apis.dtos.LoginDataDTO;
import br.com.auth.keycloak.apis.dtos.UserAuthDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthApi {

    @POST
    @Path("/login")
    public Uni<Response> login(LoginDataDTO loginDataDTO) {
        return Uni.createFrom()
                .item(
                        Response.ok(
                                UserAuthDTO.builder()
                                        .refreshToken("refresh")
                                        .token("toekn")
                                        .build()
                        ).build()
                );
    }
}
