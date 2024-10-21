package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.config.KeycloakResource;
import br.com.simoes.consultoria.auth.apis.dtos.AuthorisationDataDTO;
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
    void loginSuccess() throws IOException {
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
    void refreshTokenSuccess() throws IOException {
        LoginDataDTO loginDataDTO = new LoginDataDTO("luiz", "123456");

        var response = given()
                .contentType("application/json")
                .body(loginDataDTO)
                .when()
                .post("/auth/login");
        AuthorisationDataDTO body = response.body().as(AuthorisationDataDTO.class);
        given()
                .contentType("text/plain")
                .body(body.refreshToken())
                .when()
                .post("/auth/refresh")
                .then()
                .statusCode(200);
    }


    @Test
    void refreshTokenError() throws IOException {
        given()
                .contentType("text/plain")
                .body("""
                        eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwYjk2MDg1NS00NTdiLTQyNTItOTAwNy1kOWQxYTEyNzkxNTQifQ.
                        eyJleHAiOjE3Mjk0ODE4MjcsImlhdCI6MTcyOTQ4MDAyNywianRpIjoiMDJkZTU3NWUtNTc0Yy00MTVmLWE4Y2YtMzAzMGRkNjNiN2QwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDozMjgwMS9yZWFsbXMvY29uc3RydWN0aW9uIiwiYXVkIjoiaHR0cDovL2xvY2FsaG9zdDozMjgwMS9yZWFsbXMvY29uc3RydWN0aW9uIiwic3ViIjoiNGI4ZmRiZWEtZjVkMC00NzE5LThmZmItYTJkMTFhNzBkZWNkIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImF1dGgtcXVhcmt1cyIsInNpZCI6ImI5YmJkZjM5LTEwYWYtNGViNC1iMTY2LThlNzg1MTk1ZTJmYiIsInNjb3BlIjoiZW1haWwgcHJvZmlsZSBiYXNpYyByb2xlcyB3ZWItb3JpZ2lucyBhY3IifQ.
                        4_rXW3_2z97ZsS0TzGnS76Utmu_go7PyFmwDRUeNdTVMGi156lBI_itJnBK5usV-L_Whd8kXQtHd08B3V4o0Aw
                        """)
                .when()
                .post("/auth/refresh")
                .then()
                .statusCode(400);
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

    private CredentialRepresentation buildCredential() {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);  // Indica que a senha não é temporária
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("123456");
        return credential;
    }

}