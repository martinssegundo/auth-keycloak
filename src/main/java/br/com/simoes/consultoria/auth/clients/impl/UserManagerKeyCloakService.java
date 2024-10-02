package br.com.simoes.consultoria.auth.clients.impl;


import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.clients.exception.AuthenticationException;
import br.com.simoes.consultoria.auth.clients.exception.UserCreationException;
import br.com.simoes.consultoria.auth.configs.KeycloakConfig;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.keycloak.admin.client.common.KeycloakAdminClientConfig;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static br.com.simoes.consultoria.auth.clients.util.ExceptionUtil.buildErrorMessage;

@ApplicationScoped
@Slf4j
public class UserManagerKeyCloakService implements UserManagerService {

    private final Keycloak keycloak;
    private final KeycloakConfig keycloakConfig;

    @Inject
    public UserManagerKeyCloakService(KeycloakConfig keycloakConfig) {
        this.keycloakConfig = keycloakConfig;
        this.keycloak = buildKeycloak();
    }

    @Override
    public Uni<Void> createUser(UserDTO userDTO) {
        return Uni.createFrom().completionStage(() -> {
                    Response response = keycloak.realm(keycloakConfig.realm()).users().create(buildNewUser(userDTO));
                    if (response.getStatus() == 201) {
                        return CompletableFuture.completedFuture(null);
                    } else {
                        return CompletableFuture.failedFuture(
                                new UserCreationException(buildErrorMessage(
                                        "UserManagerKeyCloakService.createUser()",
                                        userDTO.username(),
                                        response,
                                        "Error to create user"
                                ), response.getStatus()));
                    }
                })
                .replaceWithVoid()
                .onFailure()
                .transform(error -> errorBuilder(error, userDTO.username()));

    }

    private UserCreationException errorBuilder(Throwable error, String user) {
        var messageError = buildErrorMessage(
                "UserManagerKeyCloakService.createUser()",
                user,
                error.getLocalizedMessage()
        );
        log.error(messageError);
        return new UserCreationException(messageError, HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
    }


    private UserRepresentation buildNewUser(UserDTO userDTO) {
        var user = new UserRepresentation();
        user.setUsername(userDTO.username());
        user.setEnabled(true);
        user.setEmailVerified(false);
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

    private UsersResource getKeycloakUsers(){
        var realm = keycloak.realm(keycloakConfig.realm());

        return realm.users();
    }

    private Keycloak buildKeycloak(){

        return KeycloakBuilder.builder()
                .realm(keycloakConfig.realm())
                .serverUrl(keycloakConfig.url())
                .username(keycloakConfig.user())
                .password(keycloakConfig.password())
                .clientId(keycloakConfig.clientId())
                .clientId(keycloakConfig.clientId())
                .clientSecret(keycloakConfig.clientSecret())
                .build();
    }
}
