package br.com.auth.keycloak.clients.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
public record UserDTO (
        String username,
        boolean enabled,
        boolean emailVerified,
        String firstName,
        String lastName,
        String email
) { }
