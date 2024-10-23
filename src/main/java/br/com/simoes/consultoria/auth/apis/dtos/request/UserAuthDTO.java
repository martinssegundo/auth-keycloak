package br.com.simoes.consultoria.auth.apis.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferência de dados de autenticação do usuário, contendo o token de acesso e o token de refresh.")
public class UserAuthDTO {

    @Parameter(description = "Token de acesso JWT que autoriza o usuário a acessar a API.", required = true)
    @Schema(description = "Token JWT de acesso do usuário.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Parameter(description = "Token de refresh usado para renovar o token de acesso.", required = true)
    @Schema(description = "Token de refresh que permite a renovação do token JWT de acesso.", example = "eyJhbGciOiJIUzI1NiIsIn...")
    private String refreshToken;
}

