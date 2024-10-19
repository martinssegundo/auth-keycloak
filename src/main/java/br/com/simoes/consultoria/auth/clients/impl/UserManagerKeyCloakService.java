package br.com.simoes.consultoria.auth.clients.impl;


import br.com.simoes.consultoria.auth.clients.AuthenticationService;
import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.clients.dtos.AuthorisationClientDataDTO;
import br.com.simoes.consultoria.auth.clients.dtos.CredentialDTO;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.clients.exception.AuthenticationException;
import br.com.simoes.consultoria.auth.clients.exception.UserCreationException;
import br.com.simoes.consultoria.auth.clients.rest.KeycloakLoginClient;
import br.com.simoes.consultoria.auth.clients.rest.KeycloakUserClient;
import br.com.simoes.consultoria.auth.configs.KeycloakConfig;
import br.com.simoes.consultoria.auth.configs.UserMagement;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.keycloak.admin.client.common.KeycloakAdminClientConfig;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static br.com.simoes.consultoria.auth.clients.util.ExceptionUtil.buildErrorMessage;

@ApplicationScoped
@Slf4j
public class UserManagerKeyCloakService implements UserManagerService {

    private final UserMagement userMagement;
    private final KeycloakUserClient keycloakUserClient;
    private final AuthenticationService authenticationService;
    private AuthorisationClientDataDTO authorisationClientDataDTO;

    @Inject
    public UserManagerKeyCloakService(final UserMagement userMagement,
                                      @RestClient final KeycloakUserClient keycloakUserClient,
                                      final AuthenticationService authenticationService) {
        this.userMagement = userMagement;
        this.keycloakUserClient = keycloakUserClient;
        this.authenticationService = authenticationService;
    }

    @Override
    public Uni<Void> createUser(UserDTO userDTO) {
        return doLoginUserManger()
                .flatMap(login -> keycloakUserClient.createNewUser(
                        "Bearer " + login.accessToken(),
                        userDTO
                ))
                .invoke(() -> log.info("User: {} Status: CREATED", userDTO.username()))
                .replaceWithVoid()
                .onFailure()
                .transform(error -> errorBuilder(
                                error,
                                userDTO.username(),
                                "UserManagerKeyCloakService.createUser()"
                        )
                );

    }

    private UserCreationException errorBuilder(Throwable error, String user, String method) {
        var messageError = buildErrorMessage(
                method,
                user,
                error.getLocalizedMessage()
        );
        log.error(messageError);
        return new UserCreationException(messageError, HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
    }


    private UserDTO buildUserToFirstSave(UserDTO userDTO) {
        return UserDTO.builder()
                .credentials(List.of(buildFirstCredential()))
                .email(userDTO.email())
                .enabled(userDTO.enabled())
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .build();
    }

    private CredentialDTO buildFirstCredential() {
        return CredentialDTO.builder()
                .type(CredentialRepresentation.PASSWORD)
                .temporary(true)
                .value("Us3rCr3d3nt14l")
                .build();
    }

    private Uni<AuthorisationClientDataDTO> doLoginUserManger() {
        if (Objects.isNull(authorisationClientDataDTO) ||
                authorisationClientDataDTO.expiredTime() < System.currentTimeMillis())
            return authenticationService.login(userMagement.user(), userMagement.password())
                    .onFailure()
                    .transform(error -> errorBuilder(
                                    error,
                                    userMagement.user(),
                                    "UserManagerKeyCloakService.doLoginUserManger()"
                            )
                    );

        return Uni.createFrom()
                .item(authorisationClientDataDTO);
    }

}
