package br.com.auth.keycloak.apis;

import br.com.auth.keycloak.apis.dtos.AuthorisationDataDTO;
import br.com.auth.keycloak.apis.dtos.LoginDataDTO;
import br.com.auth.keycloak.domain.entities.Login;
import br.com.auth.keycloak.domain.usecases.LoginUseCase;
import br.com.auth.keycloak.mappers.AuthenticationMapper;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class AuthApiTest {
    @Mock
    LoginUseCase loginUseCase;
    @Mock
    AuthenticationMapper authenticationMapper;
    @InjectMocks
    AuthApi authApi;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        when(loginUseCase.login(any())).thenReturn(null);
        when(authenticationMapper.convertToAuthorisationClientDataDTO(any())).thenReturn(new AuthorisationDataDTO());
        when(authenticationMapper.convertToLogin(any())).thenReturn(new Login());


  //      Uni<Response> result = authApi.login(new LoginDataDTO("user", "password"));
   //     Assertions.assertEquals(null, result);
    }
}