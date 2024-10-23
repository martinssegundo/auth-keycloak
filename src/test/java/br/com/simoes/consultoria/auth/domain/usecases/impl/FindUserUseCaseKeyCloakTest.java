package br.com.simoes.consultoria.auth.domain.usecases.impl;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.clients.exception.DefaultException;
import br.com.simoes.consultoria.auth.domain.entities.User;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@QuarkusTest
class FindUserUseCaseKeyCloakTest {
    @Mock
    UserManagerService createUserService;
    @Inject
    UserMapper userMapper;

    FindUserUseCaseKeyCloak findUserUseCaseKeyCloak;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findUserUseCaseKeyCloak = new FindUserUseCaseKeyCloak(createUserService, userMapper);
    }

    @Test
    void testFindUserWithUsernameSucess() {
        when(createUserService.findUser(anyString(), eq(10), eq(1)))
                .thenReturn(Uni.createFrom().item(
                        List.of(UserDTO.builder()
                                .username("username")
                                .id("123456")
                                .enabled(Boolean.TRUE)
                                .build())
                ));
        findUserUseCaseKeyCloak.findUser("username", 10, 1)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertItem(List.of(User.builder()
                        .username("username")
                        .userIdentifier("123456")
                        .enabled(Boolean.TRUE)
                        .build()));
    }

    @Test
    void testFindUserWithoutUsernameSucess() {
        when(createUserService.findUser(eq(null), eq(10), eq(1)))
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
        findUserUseCaseKeyCloak.findUser(null, 10, 1)
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
    void testFindUserWithUsernameWithError() {
        when(createUserService.findUser(anyString(), eq(10), eq(1)))
                .thenReturn(Uni.createFrom().failure(new DefaultException("User não encontrado", 404)));
        findUserUseCaseKeyCloak.findUser("username", 10, 1)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(DefaultException.class);
    }
}