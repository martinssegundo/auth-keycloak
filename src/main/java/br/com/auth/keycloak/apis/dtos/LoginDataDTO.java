package br.com.auth.keycloak.apis.dtos;

import lombok.*;

@Builder
public record LoginDataDTO (
        String user,
        String password
) { }
