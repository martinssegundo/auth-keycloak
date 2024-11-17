package br.com.simoes.consultoria.auth.clients.impl;


import br.com.simoes.consultoria.auth.clients.AuthenticationService;
import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.clients.dtos.AuthorisationClientDataDTO;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.clients.rest.KeycloakUserClient;
import br.com.simoes.consultoria.auth.configs.UserMagement;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Objects;

import static br.com.simoes.consultoria.auth.clients.util.HeaderUtil.bearrer;
import static br.com.simoes.consultoria.auth.clients.util.ManagerUserUtil.buildUserToFirstSave;
import static br.com.simoes.consultoria.auth.clients.util.ManagerUserUtil.creationErrorBuilder;
import static br.com.simoes.consultoria.auth.clients.util.ManagerUserUtil.listErrorBuilder;

@ApplicationScoped
@Slf4j
public class UserManagerKeyCloakService implements UserManagerService {

    private static final String[] ACTIONS = {"VERIFY_EMAIL", "UPDATE_PASSWORD"};


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
                .invoke(() -> log.info("Creting user {}", userDTO.username()))
                .flatMap(login -> keycloakUserClient.createNewUser(
                        bearrer(login.accessToken()),
                        buildUserToFirstSave(userDTO)
                ))
                .invoke(() -> log.info("User: {} Status: CREATED", userDTO.username()))
                .flatMap(response -> {
                    var location = response.getHeaderString("Location");
                    var userid = location.substring(location.lastIndexOf('/') + 1);
                    return sendActionsNewUser(userid);
                })
                .replaceWithVoid()
                .onFailure()
                .transform(error -> creationErrorBuilder(
                                error,
                                userDTO.username(),
                                "UserManagerKeyCloakService.createUser()"
                        )
                );

    }

    @Override
    public Uni<List<UserDTO>> findUserByNameOrFirstNameOrEmail(String username, Integer max, Integer page) {
        return doLoginUserManger()
                .invoke(() -> log.info("Listing users ==================================================================="))
                .flatMap(token -> keycloakUserClient.getUsersByFilter(bearrer(token.accessToken()), username, max, page))
                .onFailure()
                .transform(error -> listErrorBuilder(
                        error,
                        username,
                        "UserManagerKeyCloakService.findUser()"
                ));
    }

    @Override
    public Uni<List<UserDTO>> listAll(Integer max, Integer page) {
        return doLoginUserManger()
                .invoke(() -> log.info("Listing users ==================================================================="))
                .flatMap(token -> keycloakUserClient.getAllUsers(bearrer(token.accessToken()), max, page))
                .onFailure()
                .transform(error -> listErrorBuilder(
                        error,
                        "UserManagerKeyCloakService.listAll()"
                ));
    }

    @Override
    public Uni<Void> disableUserByUserId(String userId) {
        return doLoginUserManger()
                .invoke(() -> log.info("Disabling user: {}", userId))
                .flatMap(token -> keycloakUserClient.findUserByUserID(bearrer(token.accessToken()),userId))
                .flatMap(user -> disableUser(userId, UserDTO.disableUser(user)))
                .onFailure()
                .transform(error -> creationErrorBuilder(
                                error,
                                userMagement.user(),
                                "UserManagerKeyCloakService.doLoginUserManger()"
                        )
                );
    }

    private Uni<Void> disableUser(String userId, UserDTO userDTO){
        return doLoginUserManger()
                .map(token -> keycloakUserClient.updateUserByUserID(bearrer(token.accessToken()), userId, userDTO))
                .replaceWithVoid();
    }


    private Uni<Void> sendActionsNewUser(String userId) {
        return doLoginUserManger()
                .flatMap(token -> keycloakUserClient.triggerUserActions(userId, bearrer(token.accessToken()), ACTIONS))
                .replaceWithVoid();
    }


    private Uni<AuthorisationClientDataDTO> doLoginUserManger() {
        if (Objects.isNull(authorisationClientDataDTO) ||
                authorisationClientDataDTO.expiredTime() < System.currentTimeMillis())
            return authenticationService.login(userMagement.user(), userMagement.password())
                    .onFailure()
                    .transform(error -> creationErrorBuilder(
                                    error,
                                    userMagement.user(),
                                    "UserManagerKeyCloakService.doLoginUserManger()"
                            )
                    );

        return Uni.createFrom()
                .item(authorisationClientDataDTO);
    }

}
