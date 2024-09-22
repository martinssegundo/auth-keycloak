package br.com.simoes.consultoria.auth.clients.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDataDTO {
    private String user;
    private String password;
}
