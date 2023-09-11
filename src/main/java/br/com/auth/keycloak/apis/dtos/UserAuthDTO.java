package br.com.auth.keycloak.apis.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO {
    private String token;
    private String refreshToken;

}
