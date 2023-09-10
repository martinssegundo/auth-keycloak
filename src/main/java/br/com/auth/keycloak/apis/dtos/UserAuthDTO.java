package br.com.auth.keycloak.apis.dtos;

import lombok.*;

@Builder
@Data
@RequiredArgsConstructor
public class UserAuthDTO {
    private String token;
    private String refreshToken;

}
