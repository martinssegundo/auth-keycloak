package br.com.auth.keycloak.clients.impl;

import br.com.auth.keycloak.clients.AuthenticationService;
import br.com.auth.keycloak.clients.dtos.AuthorisationClientDataDTO;
import br.com.auth.keycloak.clients.dtos.UserDTO;
import br.com.auth.keycloak.clients.rest.KeycloakLoginClient;
import br.com.auth.keycloak.clients.rest.KeycloakUserClient;
import br.com.auth.keycloak.domain.util.AuthenticationUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class AuthenticationKeycloakService implements AuthenticationService {

    private static final String GRANT_TYPE = "password";

    private KeycloakLoginClient keycloakLoginClient;
    private KeycloakUserClient keycloakUserClient;

    public AuthenticationKeycloakService(@RestClient KeycloakLoginClient keycloakLoginClient,
                                         @RestClient KeycloakUserClient keycloakUserClient){
        this.keycloakLoginClient = keycloakLoginClient;
        this.keycloakUserClient = keycloakUserClient;
    }

    @Override
    public Uni<AuthorisationClientDataDTO> login(String login, String password) {
        log.debug("Start login with keycloak");
        return keycloakLoginClient.login(
                AuthenticationUtil.buildBasic("user", "password"),
                AuthenticationUtil.buildForm(GRANT_TYPE, "user", "password"));
    }

    @Override
    public Uni<Void> createUser(UserDTO userDTO) {
        log.debug("Create an user with keycloak");
        return keycloakUserClient.createNewUser(userDTO);
    }
}
