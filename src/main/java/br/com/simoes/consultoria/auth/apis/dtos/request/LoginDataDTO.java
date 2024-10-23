package br.com.simoes.consultoria.auth.apis.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Builder
@Schema(description = "Objeto de transferência de dados para login do usuário.")
public record LoginDataDTO (

        @JsonProperty("username")
        @NotNull
        @Valid
        @Parameter(description = "Nome de usuário ou identificador do usuário.", required = true)
        @Schema(description = "Nome de usuário que será utilizado para autenticação.", example = "john_doe")
        String user,

        @JsonProperty("password")
        @NotNull
        @Valid
        @Parameter(description = "Senha do usuário.", required = true)
        @Schema(description = "Senha associada ao nome de usuário.", example = "P@ssw0rd")
        String password
) { }
