package br.com.simoes.consultoria.auth.apis.dtos;

import lombok.*;

@Builder
public record LoginDataDTO (
        String user,
        String password
) { }
