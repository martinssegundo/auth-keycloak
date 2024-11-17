package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.clients.exception.DefaultException;
import br.com.simoes.consultoria.auth.clients.exception.FindUserException;
import br.com.simoes.consultoria.auth.domain.entities.User;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@QuarkusTest
class FindUserByLoginOrNameOrEmailUseCaseKeyCloakTest {
    @Mock
    UserManagerService createUserService;
    @Inject
    UserMapper userMapper;

    FindUserByLoginOrNameOrEmailUseCaseKeyCloak findUserUseCaseKeyCloak;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findUserUseCaseKeyCloak = new FindUserByLoginOrNameOrEmailUseCaseKeyCloak(createUserService, userMapper);
    }

    @Test
    void testFindUserByNameOrFirsNamOrEmailWithUsernameSucess() {
        when(createUserService.findUserByNameOrFirstNameOrEmail(anyString(), eq(10), eq(1)))
                .thenReturn(Uni.createFrom().item(
                        List.of(UserDTO.builder()
                                .username("username")
                                .id("123456")
                                .enabled(Boolean.TRUE)
                                .build())
                ));
        findUserUseCaseKeyCloak.findUserByNameOrFirsNamOrEmail("username", 10, 1)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertItem(List.of(User.builder()
                        .username("username")
                        .userIdentifier("123456")
                        .enabled(Boolean.TRUE)
                        .build()));
    }

    @Test
    void testFindUserByNameOrFirsNamOrEmailWithUsernameErrorWhenUserNotFound() {
        when(createUserService.findUserByNameOrFirstNameOrEmail(anyString(), eq(10), eq(1)))
                .thenReturn(Uni.createFrom().failure(new FindUserException("User not found", HttpStatus.SC_NO_CONTENT)));
        findUserUseCaseKeyCloak.findUserByNameOrFirsNamOrEmail("username", 10, 1)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(FindUserException.class);
    }

    @Test
    void testFindUserByNameOrFirsNamOrEmailWithoutUsernameSucess() {
        when(createUserService.findUserByNameOrFirstNameOrEmail(eq(null), eq(10), eq(1)))
                .thenReturn(Uni.createFrom().item(
                        List.of(UserDTO.builder()
                                        .username("username")
                                        .id("123456")
                                        .enabled(Boolean.TRUE)
                                        .build(),
                                UserDTO.builder()
                                        .username("username2")
                                        .id("223456")
                                        .enabled(Boolean.TRUE)
                                        .build())
                ));
        findUserUseCaseKeyCloak.findUserByNameOrFirsNamOrEmail(null, 10, 1)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertItem(
                        List.of(User.builder()
                                        .username("username")
                                        .userIdentifier("123456")
                                        .enabled(Boolean.TRUE)
                                        .build(),
                                User.builder()
                                        .username("username2")
                                        .userIdentifier("223456")
                                        .enabled(Boolean.TRUE)
                                        .build())
                );
    }

    @Test
    void testFindUserByNameOrFirsNamOrEmailWithUsernameWithError() {
        when(createUserService.findUserByNameOrFirstNameOrEmail(anyString(), eq(10), eq(1)))
                .thenReturn(Uni.createFrom().failure(new DefaultException("User não encontrado", 404)));
        findUserUseCaseKeyCloak.findUserByNameOrFirsNamOrEmail("username", 10, 1)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(DefaultException.class);
    }
}