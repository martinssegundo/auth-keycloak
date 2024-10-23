package br.com.simoes.consultoria.auth.apis.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Builder
@Schema(description = "Objeto de transferência de dados de autorização que contém informações sobre o token de acesso e o token de refresh.")
public record AuthorisationDataDTO (

        @JsonProperty("access_token")
        @Parameter(description = "Token de acesso JWT fornecido ao usuário após a autenticação.", required = true)
        @Schema(description = "Token de acesso JWT para autorizar o usuário.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @JsonProperty("expires_in")
        @Parameter(description = "Tempo em segundos até a expiração do token de acesso.", required = true)
        @Schema(description = "Número de segundos até o token de acesso expirar.", example = "3600")
        Long expiresIn,

        @JsonProperty("refresh_expires_in")
        @Parameter(description = "Tempo em segundos até o token de refresh expirar.", required = true)
        @Schema(description = "Número de segundos até o token de refresh expirar.", example = "18000")
        Long refreshExpiresIn,

        @JsonProperty("refresh_token")
        @Parameter(description = "Token de refresh JWT fornecido para renovar o token de acesso.", required = true)
        @Schema(description = "Token de refresh JWT para renovar o token de acesso.", example = "eyJhbGciOiJIUzI1NiIsIn...")
        String refreshToken,

        @JsonProperty("token_type")
        @Parameter(description = "Tipo do token, geralmente 'Bearer'.", required = true)
        @Schema(description = "Tipo do token, geralmente 'Bearer'.", example = "Bearer")
        String tokenType,

        @JsonProperty("not-before-policy")
        @Parameter(description = "Política de 'not-before' que define a partir de quando o token é válido.", required = true)
        @Schema(description = "Política 'not-before' do token.", example = "0")
        Long notBeforePolicy,

        @JsonProperty("session_state")
        @Parameter(description = "Estado da sessão durante a autenticação.", required = true)
        @Schema(description = "Identificador do estado da sessão.", example = "1234567890abcdef")
        String sessionState,

        @JsonProperty("scope")
        @Parameter(description = "Escopo de autorização concedido ao usuário.", required = true)
        @Schema(description = "Escopo de autorização do token.", example = "openid email profile")
        String scope
) { }
