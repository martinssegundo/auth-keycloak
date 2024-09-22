package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.dtos.AuthorisationDataDTO;
import br.com.simoes.consultoria.auth.domain.entities.Login;
import br.com.simoes.consultoria.auth.domain.usecases.LoginUseCase;
import br.com.simoes.consultoria.auth.mappers.AuthenticationMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.Description;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@QuarkusTest
class AuthApiTest {
    @Mock
    LoginUseCase loginUseCase;
    @Mock
    AuthenticationMapper authenticationMapper;
    @InjectMocks
    AuthApi authApi;

    static DockerComposeContainer<?> environment;

    @BeforeAll
    public static void setUp() {
        environment = new DockerComposeContainer<>(new File("src/test/resources/compose/docker-compose.yaml"))
                .withExposedService("keycloak_1", 8080)
                .withExposedService("postgres_1", 5432);
        environment.starting(Description.EMPTY);
    }

    @AfterAll
    public static void tearDown() {
        if (environment != null) {
            environment.finished(Description.EMPTY);
        }
    }

    @BeforeEach
    void setUpEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        when(loginUseCase.login(any())).thenReturn(null);
        when(authenticationMapper.convertToAuthorisationClientDataDTO(any())).thenReturn(AuthorisationDataDTO.builder().build());
        when(authenticationMapper.convertToLogin(any())).thenReturn(new Login());


  //      Uni<Response> result = authApi.login(new LoginDataDTO("user", "password"));
   //     Assertions.assertEquals(null, result);
    }
}