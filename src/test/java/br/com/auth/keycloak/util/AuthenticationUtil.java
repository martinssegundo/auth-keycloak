package br.com.auth.keycloak.util;

import br.com.auth.keycloak.clients.dtos.AuthorisationDataDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AuthenticationUtil {


    public static AuthorisationDataDTO getAuthentication() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/exemplo.json")), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        AuthorisationDataDTO authorization = objectMapper.readValue(json, AuthorisationDataDTO.class);
        return authorization;
    }
}
