package br.com.simoes.consultoria.auth.clients.dtos;

import lombok.Builder;


@Builder
public record UserDTO (
        String username,
        boolean enabled,
        boolean emailVerified,
        String firstName,
        String lastName,
        String email
) { }
