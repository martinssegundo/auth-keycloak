package br.com.auth.keycloak.clients.impl;

import br.com.auth.keycloak.clients.AuthenticationService;
import br.com.auth.keycloak.clients.dtos.AuthorisationClientDataDTO;
import br.com.auth.keycloak.clients.dtos.UserDTO;
import br.com.auth.keycloak.clients.rest.KeycloakLoginClient;
import br.com.auth.keycloak.clients.rest.KeycloakUserClient;
import br.com.auth.keycloak.configs.KeycloakConfig;
import br.com.auth.keycloak.domain.utils.AuthenticationUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class AuthenticationKeycloakService implements AuthenticationService {

    private static final String GRANT_TYPE = "password";

    private final KeycloakLoginClient keycloakLoginClient;
    private final KeycloakUserClient keycloakUserClient;

    private final KeycloakConfig keycloakConfig;

    @Inject
    public AuthenticationKeycloakService(@RestClient final KeycloakLoginClient keycloakLoginClient,
                                         @RestClient final KeycloakUserClient keycloakUserClient,
                                         final KeycloakConfig keycloakConfig){
        this.keycloakLoginClient = keycloakLoginClient;
        this.keycloakUserClient = keycloakUserClient;
        this.keycloakConfig = keycloakConfig;
    }

    @Override
    public Uni<AuthorisationClientDataDTO> login(String login, String password) {
        log.debug("Start login with keycloak");
        return keycloakLoginClient.login(
                AuthenticationUtil.buildBasic(login, password),
                AuthenticationUtil.buildForm(GRANT_TYPE, login, password));
    }
}
