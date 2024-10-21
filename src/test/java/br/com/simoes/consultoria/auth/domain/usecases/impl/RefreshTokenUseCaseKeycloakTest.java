package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.AuthenticationService;
import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.domain.entities.Authorization;
import br.com.simoes.consultoria.auth.domain.usecases.RefreshTokenUseCase;
import br.com.simoes.consultoria.auth.mappers.AuthenticationMapper;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import br.com.simoes.consultoria.auth.util.AuthenticationUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;


@QuarkusTest
class RefreshTokenUseCaseKeycloakTest {
    @Mock
    AuthenticationService authenticationService;

    @Inject
    AuthenticationMapper authenticationMapper;
    RefreshTokenUseCase refreshTokenUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        refreshTokenUseCase = new RefreshTokenUseCaseKeycloak(authenticationService, authenticationMapper);
    }

    @Test
    void testRefreshSucess() throws IOException {
        var dataDTO = AuthenticationUtil.getAuthentication();
        when(authenticationService.refresh(anyString())).thenReturn(Uni.createFrom().item(dataDTO));

        refreshTokenUseCase.refresh("refreshToken")
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertCompleted();
    }
}
