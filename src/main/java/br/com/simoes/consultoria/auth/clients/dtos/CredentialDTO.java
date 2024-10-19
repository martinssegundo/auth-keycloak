package br.com.simoes.consultoria.auth.clients.dtos;


import lombok.Builder;

@Builder
public record CredentialDTO (
        String type,
        String value,
        boolean temporary
) {
}
