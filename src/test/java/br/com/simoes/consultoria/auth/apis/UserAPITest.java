package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.config.KeycloakResource;
import br.com.simoes.consultoria.auth.apis.dtos.AuthorisationDataDTO;
import br.com.simoes.consultoria.auth.apis.dtos.LoginDataDTO;
import br.com.simoes.consultoria.auth.apis.dtos.UserCreateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(KeycloakResource.class)
@Slf4j
class UserAPITest {
    @Inject
    UserAPI userAPI;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserSucess() throws JsonProcessingException {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildUserCreteDTO(
                        "luiz.segundo",
                        "Luiz",
                        "Segundo",
                        "luiz.segundo@email.com"
                ))
                .header(new Header("Authorization", token("luiz", "123456")))
                .when()
                .post("/user")
                .then()
                .statusCode(201);
    }



    @Test
    void testCreateUserErroSameUser() throws JsonProcessingException {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildUserCreteDTO(
                        "luiz.segundo",
                        "Luiz",
                        "Segundo",
                        "luiz.segundo@email.com"
                ))
                .header(new Header("Authorization", token("maria", "mariaPassword123")))
                .when()
                .post("/user")
                .then()
                .statusCode(500);
    }



    private UserCreateDTO buildUserCreteDTO(String username,
                                            String firstName,
                                            String lastName,
                                            String email) {
        return UserCreateDTO.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .build();
    }


    private String token(String user, String password) throws JsonProcessingException {
        var loginDataDTO = new LoginDataDTO(user, password);

        var response = given()
                .contentType("application/json")
                .body(loginDataDTO)
                .when()
                .post("/auth/login")
                .thenReturn();

        var authorisationDTO = mapper.readValue(response.body().print(),AuthorisationDataDTO.class);
        return "Bearer "+authorisationDTO.accessToken();
    }
}