package br.com.auth.keycloak.domain.usecases.impl;

import br.com.auth.keycloak.clients.AuthenticationService;
import br.com.auth.keycloak.clients.dtos.AuthorisationClientDataDTO;
import br.com.auth.keycloak.domain.entities.Authorization;
import br.com.auth.keycloak.domain.entities.Login;
import br.com.auth.keycloak.mappers.AuthenticationMapper;
import br.com.auth.keycloak.util.AuthenticationUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@QuarkusTest
class LoginUseCaseKeycloakTest {
    @Mock
    AuthenticationService authenticationService;
    @Mock
    AuthenticationMapper authenticationMapper;
    LoginUseCaseKeycloak loginUseCaseKeycloak;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginUseCaseKeycloak = new LoginUseCaseKeycloak(authenticationService, authenticationMapper);
    }

    @Test
    void testLogin() throws IOException {
        var dataDTO = AuthenticationUtil.getAuthentication();
        var dataAuth = buildAuthorization(dataDTO);
        when(authenticationService.login(any(),any())).thenReturn(Uni.createFrom().item(dataDTO));
        when(authenticationMapper.convertToAuthorization(dataDTO)).thenReturn(dataAuth);
        Uni<Authorization> result = loginUseCaseKeycloak.login(new Login());
        result.subscribe()
                .with(data -> {
                    assertEquals(data, dataAuth);
                });
    }

    private Authorization buildAuthorization(AuthorisationClientDataDTO dataDTO){
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
