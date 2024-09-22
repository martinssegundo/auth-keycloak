package br.com.simoes.consultoria.auth.clients.impl;


import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.clients.exception.UserCreationExeption;
import br.com.simoes.consultoria.auth.configs.KeycloakConfig;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class UserManagerKeyCloakService implements UserManagerService {

    private final Keycloak keycloak;
    private final KeycloakConfig keycloakConfig;

    @Override
    public Uni<Void> createUser(UserDTO userDTO){
        Response response = keycloak.realm(keycloakConfig.realm())
                .users()
                .create(buildNewUser(userDTO));
        if(response.getStatus() == 201)
            return Uni.createFrom().voidItem();
        else
            return Uni.createFrom()
                    .failure(() -> new UserCreationExeption("Error to create user", response.getStatus()));
    }


    private UserRepresentation buildNewUser(UserDTO userDTO) {
        var user = new UserRepresentation();
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        return user;
    }

    private Uni<Void> sendResetPasswordEmail(String userId) {
        keycloak.realm(keycloakConfig.realm())
                .users()
                .get(userId)
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));
        return Uni.createFrom()
                .voidItem();
    }
}
