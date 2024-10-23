package br.com.simoes.consultoria.auth.apis.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Builder
@Schema(description = "Objeto de transferência de dados para a criação de um novo usuário.")
public record UserCreateDTO(

        @JsonProperty("username")
        @NotNull
        @Valid
        @Parameter(description = "Nome de usuário para o novo usuário.", required = true)
        @Schema(description = "Nome de usuário exclusivo.", example = "john_doe")
        String username,

        @JsonProperty("first-name")
        @NotNull
        @Valid
        @Parameter(description = "Primeiro nome do usuário.", required = true)
        @Schema(description = "Primeiro nome do usuário.", example = "John")
        String firstName,

        @JsonProperty("last-name")
        @NotNull
        @Valid
        @Parameter(description = "Último nome do usuário.", required = true)
        @Schema(description = "Último nome do usuário.", example = "Doe")
        String lastName,

        @JsonProperty("email")
        @NotNull
        @Valid
        @Parameter(description = "Endereço de email do usuário.", required = true)
        @Schema(description = "Email do usuário, deve ser único.", example = "john.doe@example.com")
        String email
) { }

