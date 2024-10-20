package br.com.simoes.consultoria.auth.clients.dtos;

import lombok.Builder;

import java.util.List;


@Builder
public record UserDTO (
        String username,
        String firstName,
        String lastName,
        String email,
        boolean enabled,
        boolean emailVerified,
        List<CredentialDTO> credentials
) { }
