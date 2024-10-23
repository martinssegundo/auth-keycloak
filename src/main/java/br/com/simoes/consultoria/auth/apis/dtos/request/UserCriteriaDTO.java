package br.com.simoes.consultoria.auth.apis.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Schema(description = "Critérios de busca para pesquisar usuários no sistema.")
public record UserCriteriaDTO(

        @JsonProperty("search")
        @Parameter(description = "Campo de pesquisa que pode ser usado para buscar pelo nome de usuário (username), primeiro nome (first-name) ou email.")
        @Schema(description = "Campo de busca que suporta pesquisa por username, first-name ou email.", example = "john_doe ou John ou john@example.com")
        String search,

        @JsonProperty("max")
        @Parameter(description = "Número máximo de usuários a serem retornados.")
        @Schema(description = "Número máximo de usuários a serem retornados.", example = "10", defaultValue = "20")
        Integer maxUser,

        @JsonProperty("page")
        @Parameter(description = "Número da página de resultados.")
        @Schema(description = "Número da página de resultados para paginação.", example = "1", defaultValue = "0")
        Integer page
) {
}