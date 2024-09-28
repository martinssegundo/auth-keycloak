package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.domain.entities.User;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@QuarkusTest
class CreateUserKeycloakUseCaseTest {
    @InjectMock
    UserManagerService createUserService;
    @InjectMock
    UserMapper userMapper;
    @InjectMock
    CreateUserKeycloakUseCase createUserKeycloakUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        when(createUserService.createUser(any(UserDTO.class))).thenReturn(Uni.createFrom().voidItem());

        Uni<Void> result = createUserKeycloakUseCase.createUser(new User());
        Assertions.assertEquals(null, result);
    }
}