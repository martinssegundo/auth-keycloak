package br.com.simoes.consultoria.auth.apis.dtos.response;

import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.time.LocalDateTime;

@Builder
@Schema(description = "Objeto que representa os detalhes de um erro ocorrido na aplicação.")
public record ErrorDTO (

        @Parameter(description = "Código de status HTTP associado ao erro.", required = true)
        @Schema(description = "Código de status HTTP retornado pela API.", example = "404")
        Integer status,

        @Parameter(description = "Mensagem descritiva do erro.", required = true)
        @Schema(description = "Mensagem de erro detalhada.", example = "Resource not found")
        String message,

        @Parameter(description = "Data e hora em que o erro ocorreu.", required = true)
        @Schema(description = "Timestamp que indica quando o erro ocorreu.", example = "2024-10-20T12:34:56")
        LocalDateTime dateTimeError
) {
}

