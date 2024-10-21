package br.com.simoes.consultoria.auth.clients.impl;

import br.com.simoes.consultoria.auth.clients.AuthenticationService;
import br.com.simoes.consultoria.auth.clients.dtos.AuthorisationClientDataDTO;
import br.com.simoes.consultoria.auth.clients.exception.AuthenticationException;
import br.com.simoes.consultoria.auth.clients.rest.KeycloakLoginClient;
import br.com.simoes.consultoria.auth.configs.KeycloakConfig;
import br.com.simoes.consultoria.auth.domain.utils.AuthenticationUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import static br.com.simoes.consultoria.auth.clients.util.ExceptionUtil.buildErrorMessage;

@ApplicationScoped
@Slf4j
public class AuthenticationKeycloakService implements AuthenticationService {

    private static final String GRANT_TYPE_LOGIN = "password";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";


    private final KeycloakLoginClient keycloakLoginClient;

    private final KeycloakConfig keycloakConfig;

    @Inject
    public AuthenticationKeycloakService(@RestClient final KeycloakLoginClient keycloakLoginClient,
                                         final KeycloakConfig keycloakConfig) {
        this.keycloakLoginClient = keycloakLoginClient;
        this.keycloakConfig = keycloakConfig;
    }

    @Override
    public Uni<AuthorisationClientDataDTO> login(String login, String password) {
        log.info("AuthenticationKeycloakService.login() =========> Start keycloak login");
        return keycloakLoginClient.login(
                        AuthenticationUtil.buildBasic(login, password),
                        AuthenticationUtil.buildFormLogin(
                                GRANT_TYPE_LOGIN,
                                login,
                                password,
                                keycloakConfig.clientId(),
                                keycloakConfig.clientSecret()
                        )
                )
                .onFailure()
                .transform(error -> {
                    if (error instanceof ClientWebApplicationException) {
                        return errorBuilderLogin((ClientWebApplicationException) error, login);
                    }
                    return error;
                });
    }

    @Override
    public Uni<AuthorisationClientDataDTO> refresh(String refreshToken) {
        log.info("AuthenticationKeycloakService.refresh() =========> Start keycloak refresh token");
        return keycloakLoginClient.refreshToken(
                        AuthenticationUtil.buildFormRefreshToken(
                                GRANT_TYPE_REFRESH_TOKEN,
                                keycloakConfig.clientId(),
                                keycloakConfig.clientSecret(),
                                refreshToken
                        )
                )
                .onFailure()
                .transform(error -> {
                    if (error instanceof ClientWebApplicationException) {
                        return errorBuilderRefreshToken((ClientWebApplicationException) error);
                    }
                    return error;
                });
    }

    private AuthenticationException errorBuilderLogin(ClientWebApplicationException error, String login){
        var response = error.getResponse();
        var messageError = buildErrorMessage(
                "AuthenticationKeycloakService.login()",
                login,
                response,
                error.getLocalizedMessage()
        );
        log.error(messageError);
        return new AuthenticationException(response.getStatus(), messageError);
    }

    private AuthenticationException errorBuilderRefreshToken(ClientWebApplicationException error){
        var response = error.getResponse();
        var messageError = buildErrorMessage(
                "AuthenticationKeycloakService.refresh()",
                response,
                error.getLocalizedMessage()
        );
        log.error(messageError);
        return new AuthenticationException(response.getStatus(), messageError);
    }

}
