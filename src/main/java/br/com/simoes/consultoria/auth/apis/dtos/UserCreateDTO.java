package br.com.simoes.consultoria.auth.apis.dtos;

import lombok.Builder;

@Builder
public record UserCreateDTO(
        String username,
        boolean enabled,
        boolean emailVerified,
        String firstName,
        String lastName,
        String email
) { }
