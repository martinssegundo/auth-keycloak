package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.dtos.AuthorisationDataDTO;
import br.com.simoes.consultoria.auth.domain.entities.Login;
import br.com.simoes.consultoria.auth.domain.usecases.LoginUseCase;
import br.com.simoes.consultoria.auth.mappers.AuthenticationMapper;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.Description;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.PostgreSQLContainer;

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


    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");


    static KeycloakContainer keycloak = new KeycloakContainer()
            .withRealmImportFile("/jsons/authentication.json");

    @BeforeAll
    public static void setup() {
        // Configura a URL do Keycloak e banco de dados PostgreSQL para os testes
        System.setProperty("quarkus.oidc.auth-server-url", keycloak.getAuthServerUrl() + "/json");
        System.setProperty("quarkus.datasource.jdbc.url", postgres.getJdbcUrl());
        System.setProperty("quarkus.datasource.username", postgres.getUsername());
        System.setProperty("quarkus.datasource.password", postgres.getPassword());

        postgres.start();
        keycloak.start();
    }

    @AfterAll
    public static void shutdown() {
        postgres.stop();
        keycloak.stop();
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