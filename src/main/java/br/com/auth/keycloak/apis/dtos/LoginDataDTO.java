package br.com.auth.keycloak.apis.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDataDTO {
    private String user;
    private String password;
}
