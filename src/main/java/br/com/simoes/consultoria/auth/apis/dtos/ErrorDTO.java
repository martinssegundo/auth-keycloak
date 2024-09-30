package br.com.simoes.consultoria.auth.apis.dtos;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorDTO (
        Integer status,
        String message,
        LocalDateTime dateTimeError
) {
}
