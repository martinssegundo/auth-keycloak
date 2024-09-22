package br.com.simoes.consultoria.auth.apis.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO {
    private String token;
    private String refreshToken;

}
