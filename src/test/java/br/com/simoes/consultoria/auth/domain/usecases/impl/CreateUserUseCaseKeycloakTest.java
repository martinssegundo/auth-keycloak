package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.clients.exception.UserCreationException;
import br.com.simoes.consultoria.auth.domain.entities.User;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@QuarkusTest
class CreateUserUseCaseKeycloakTest {
    @Mock
    UserManagerService createUserService;

    @Inject
    UserMapper userMapper;
    CreateUserUseCaseKeycloak createUserUseCaseKeycloak;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUserUseCaseKeycloak = new CreateUserUseCaseKeycloak(createUserService, userMapper);
    }

    @Test
    void testCreateUserSucess() {
        when(createUserService.createUser(any(UserDTO.class)))
                .thenReturn(Uni.createFrom().voidItem());

        Uni<Void> result = createUserUseCaseKeycloak.createUser(new User());
        Assertions.assertEquals(Uni.createFrom().voidItem(), result);
    }

    @Test
    void testCreateUserError() {
        when(createUserService.createUser(any(UserDTO.class)))
                .thenReturn(
                        Uni.createFrom()
                                .failure(new UserCreationException(
                                        "Error to create user",
                                        409
                                ))
                );

        createUserUseCaseKeycloak.createUser(buildUser())
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertFailed();

    }


    private User buildUser() {
        var user = new User();
        user.setEmail("email@email.com.br");
        user.setFirstName("Luiz");
        user.setLastName("Segundo");
        return user;
    }
}