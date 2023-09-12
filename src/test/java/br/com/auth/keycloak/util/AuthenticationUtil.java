package br.com.auth.keycloak.util;

import br.com.auth.keycloak.clients.dtos.AuthorisationClientDataDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AuthenticationUtil {


    public static AuthorisationClientDataDTO getAuthentication() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/jsons/authentication.json")), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        AuthorisationClientDataDTO authorization = objectMapper.readValue(json, AuthorisationClientDataDTO.class);
        return authorization;
    }
}
