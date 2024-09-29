package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.config.KeycloakResource;
import br.com.simoes.consultoria.auth.apis.dtos.LoginDataDTO;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.util.Collections;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(KeycloakResource.class)
@Slf4j
class AuthApiTest {

    @Inject
    AuthApi authApi;


    /*static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");


    static KeycloakContainer keycloak = new KeycloakContainer()
            .withRealmImportFile("/jsons/authentication.json");

    @BeforeAll
    public static void setup() {
        postgres.start();
        keycloak.start();
        // Configs Keycloak
        System.setProperty("quarkus.keycloak.admin-client.url", keycloak.getAuthServerUrl());
        System.setProperty("keycloak-login-api/mp-rest/url", keycloak.getAuthServerUrl() + "/realms/construction/protocol/openid-connect");
        System.setProperty("quarkus.keycloak.admin-client.server-url", keycloak.getAuthServerUrl() + "/realms/construction");
        System.setProperty("quarkus.oidc.auth-server-url", keycloak.getAuthServerUrl() + "/realms/construction");

        var cliente = keycloak.getKeycloakAdminClient()
                .realm("construction")
                .clients()
                .findByClientId("auth-quarkus")
                .stream().findFirst().get();

        System.setProperty("quarkus.keycloak.admin-client.client-id",  cliente.getClientId());
        System.setProperty("quarkus.keycloak.admin-client.client-secret", cliente.getSecret());
        System.setProperty("quarkus.oidc.client-id",  cliente.getClientId());
        System.setProperty("quarkus.oidc.credentials.secret", cliente.getSecret());


        //Configs database
        System.setProperty("quarkus.datasource.jdbc.url", postgres.getJdbcUrl());
        System.setProperty("quarkus.datasource.username", postgres.getUsername());
        System.setProperty("quarkus.datasource.password", postgres.getPassword());
    }

    @AfterAll
    public static void shutdown() {
        postgres.stop();
        keycloak.stop();
    }*/


    @BeforeEach
    void setUpEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() throws IOException {
        LoginDataDTO loginDataDTO = new LoginDataDTO("luiz", "123456");

        given()
                .contentType("application/json")
                .body(loginDataDTO)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200);

    }


    private UserRepresentation buildNewUser() {
        var user = new UserRepresentation();
        user.setUsername("luiz");
        user.setEnabled(true);
        user.setEmailVerified(false);
        user.setCredentials(Collections.singletonList(buildCredential()));
        return user;
    }

    private CredentialRepresentation buildCredential(){
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);  // Indica que a senha não é temporária
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("123456");
        return credential;
    }

}