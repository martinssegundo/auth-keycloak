package br.com.simoes.consultoria.auth.apis.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Schema(description = "Objeto de resposta que contém informações detalhadas sobre o usuário.")
public record UserResponseDTO(

        @JsonProperty("userId")
        @Parameter(description = "Identificador único do usuário.", required = true)
        @Schema(description = "ID único do usuário no sistema.", example = "123e4567-e89b-12d3-a456-426614174000")
        String userIdentifier,

        @JsonProperty("username")
        @Parameter(description = "Nome de usuário do sistema.", required = true)
        @Schema(description = "Nome de usuário único.", example = "john_doe")
        String username,

        @JsonProperty("email")
        @Parameter(description = "Endereço de email do usuário.", required = true)
        @Schema(description = "Email associado ao usuário.", example = "john.doe@example.com")
        String email,

        @JsonProperty("enabled")
        @Parameter(description = "Indica se a conta do usuário está ativa.", required = true)
        @Schema(description = "Estado ativo do usuário. True se o usuário estiver ativo, False se estiver desativado.", example = "true")
        boolean enabled,

        @JsonProperty("first-name")
        @Parameter(description = "Primeiro nome do usuário.", required = true)
        @Schema(description = "Primeiro nome do usuário.", example = "John")
        String firstName,

        @JsonProperty("last-name")
        @Parameter(description = "Último nome do usuário.", required = true)
        @Schema(description = "Último nome do usuário.", example = "Doe")
        String lastName
) {
}

