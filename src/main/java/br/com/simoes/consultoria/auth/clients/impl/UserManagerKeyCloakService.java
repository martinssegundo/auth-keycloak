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

import static br.com.simoes.consultoria.auth.clients.util.ManagerUserUtil.*;

@ApplicationScoped
@Slf4j
public class UserManagerKeyCloakService implements UserManagerService {

    private static final String[] ACTIONS = {"VERIFY_EMAIL", "UPDATE_PASSWORD"};
    private static final String BEARER = "Bearer ";


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
                        BEARER + login.accessToken(),
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
    public Uni<List<UserDTO>> findUser(String username, Integer max, Integer page) {
        return doLoginUserManger()
                .invoke(() -> log.info("Listing users ==================================================================="))
                .flatMap(token -> sarchStrategy(BEARER+token.accessToken(),username,max,page))
                .onFailure()
                .transform(error -> listErrorBuilder(
                        error,
                        username,
                        "UserManagerKeyCloakService.findUser()"
                ));
    }

    private Uni<List<UserDTO>> sarchStrategy(String accessToken, String username, Integer max, Integer page) {
        return username == null
                ? keycloakUserClient.getUsers(accessToken,max,page)
                : keycloakUserClient.getUsers(accessToken,username,max,page);

    }

    private Uni<Void> sendActionsNewUser(String userId){
        return doLoginUserManger()
                .flatMap(token -> keycloakUserClient.triggerUserActions(userId,BEARER+ token.accessToken(),ACTIONS))
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
