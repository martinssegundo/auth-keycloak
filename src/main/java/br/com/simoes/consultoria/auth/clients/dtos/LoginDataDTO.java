package br.com.simoes.consultoria.auth.clients.dtos;

import lombok.*;

@Builder
public record LoginDataDTO (
        String user,
        String password
) { }
