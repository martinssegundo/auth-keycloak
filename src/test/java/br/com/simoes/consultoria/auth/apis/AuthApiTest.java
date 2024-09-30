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
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
@QuarkusTestResource(KeycloakResource.class)
@Slf4j
class AuthApiTest {

    @Inject
    AuthApi authApi;


    @BeforeEach
    void setUpEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginSucess() throws IOException {
        LoginDataDTO loginDataDTO = new LoginDataDTO("luiz", "123456");

        given()
                .contentType("application/json")
                .body(loginDataDTO)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200);

    }


    @Test
    void loginFail() throws IOException {
        LoginDataDTO loginDataDTO = new LoginDataDTO("luiz", "1234567");

        given()
                .contentType("application/json")
                .body(loginDataDTO)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401)
                .body("message", containsString("401"));

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