package br.com.auth.keycloak.apis.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
public class LoginDataDTO {
    private String login;
    private String password;
}
