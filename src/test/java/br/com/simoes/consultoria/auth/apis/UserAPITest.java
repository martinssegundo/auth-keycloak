package br.com.simoes.consultoria.auth.apis;

import br.com.simoes.consultoria.auth.apis.config.KeycloakResource;
import br.com.simoes.consultoria.auth.apis.dtos.response.AuthorisationDataDTO;
import br.com.simoes.consultoria.auth.apis.dtos.request.LoginDataDTO;
import br.com.simoes.consultoria.auth.apis.dtos.request.UserCreateDTO;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.clients.rest.KeycloakUserClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.CredentialRepresentation;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
@QuarkusTestResource(KeycloakResource.class)
@Slf4j
class UserAPITest {

    @InjectMock
    @RestClient
    KeycloakUserClient keycloakUserClient;

    @Inject
    UserAPI userAPI;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        when(keycloakUserClient.triggerUserActions(any(), any(), any(String[].class)))
                .thenReturn(Uni.createFrom().item(Response.status(200).header("Location", "/123456").build()));
    }

    @Test
    void testCreateUserSuccess() throws JsonProcessingException {
        when(keycloakUserClient.createNewUser(
                any(),
                any()))
                .thenReturn(Uni.createFrom().item(Response.status(201).header("Location", "/123456").build()));
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
    void testFindUserSuccess() throws JsonProcessingException {
        when(keycloakUserClient.createNewUser(
                any(),
                any()))
                .thenReturn(Uni.createFrom().item(Response.status(201).header("Location", "/123456").build()));
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
    void testCreateUserError() throws JsonProcessingException {
        when(keycloakUserClient.createNewUser(
                any(),
                any()))
                .thenReturn(Uni.createFrom().failure(new RuntimeException("Error create user")));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildUserCreteDTO(
                        "luiz.segundo.new",
                        "Luiz",
                        "Segundo",
                        "luiz.segundo.novo@email.com"
                ))
                .header(new Header("Authorization", token("luiz", "123456")))
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

    private UserDTO buildUserDTO(UserCreateDTO userCreateDTO){
        return  UserDTO.builder()
                .username(userCreateDTO.username())
                .email(userCreateDTO.email())
                .firstName(userCreateDTO.firstName())
                .lastName(userCreateDTO.lastName())
                .enabled(Boolean.TRUE)
                .emailVerified(Boolean.FALSE)
                .credentials(List.of(buildFirstCredential()))
                .requiredActions(List.of())
                .groups(List.of())
                .build();
    }

    private CredentialRepresentation buildFirstCredential() {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue("Us3rCr3d3nt14l");
        return credentialRepresentation;
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