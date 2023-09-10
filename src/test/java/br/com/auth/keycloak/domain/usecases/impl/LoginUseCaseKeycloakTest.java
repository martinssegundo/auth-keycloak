package br.com.auth.keycloak.domain.usecases.impl;

import br.com.auth.keycloak.clients.KeycloakClient;
import br.com.auth.keycloak.clients.dtos.AuthorisationDataDTO;
import br.com.auth.keycloak.domain.entities.Authorization;
import br.com.auth.keycloak.domain.entities.Login;
import br.com.auth.keycloak.util.AuthenticationUtil;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@QuarkusTest
class LoginUseCaseKeycloakTest {
    @Mock
    KeycloakClient keycloakClient;
    LoginUseCaseKeycloak loginUseCaseKeycloak;

    @BeforeEach
    void setUp() {
        loginUseCaseKeycloak = new LoginUseCaseKeycloak(keycloakClient);
    }

    @Test
    void testLogin() throws IOException {
        var dataDTO = AuthenticationUtil.getAuthentication();
        when(keycloakClient.login(any(),any())).thenReturn(Uni.createFrom().item(dataDTO));
        Uni<Authorization> result = loginUseCaseKeycloak.login(new Login());
        result.subscribe()
                .with(data -> {
                    assertEquals(data, buildAuthorization(dataDTO));
                });
    }

    private Authorization buildAuthorization(AuthorisationDataDTO dataDTO){
        return Authorization.builder()
                .accessToken(dataDTO.getAccessToken())
                .notBeforePolicy(dataDTO.getNotBeforePolicy())
                .refreshExpiresIn(dataDTO.getRefreshExpiresIn())
                .refreshToken(dataDTO.getRefreshToken())
                .scope(dataDTO.getScope())
                .sessionState(dataDTO.getSessionState())
                .expiresIn(dataDTO.getExpiresIn())
                .tokenType(dataDTO.getTokenType())
                .build();
    }
}
