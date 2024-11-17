package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class DisableUserByUserIdUseCaseKeycloakTest {
    @Mock
    UserManagerService disableUserService;
    @InjectMocks
    DisableUserByUserIdUseCaseKeycloak disableUserByUserIdUseCaseKeycloak;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDisableUser() {
        when(disableUserService.disableUserByUserId(anyString())).thenReturn(null);

        Uni<Void> result = disableUserByUserIdUseCaseKeycloak.disableUser("userId");
        Assertions.assertEquals(null, result);
    }
}
