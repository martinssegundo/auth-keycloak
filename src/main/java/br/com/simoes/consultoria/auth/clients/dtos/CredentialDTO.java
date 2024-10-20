package br.com.simoes.consultoria.auth.clients.dtos;


import lombok.Builder;

@Builder
public record CredentialDTO (
        String id,
        String type,
        String userLabel,
        Long createdDate,
        String secretData,
        String credentialData,
        Integer priority,
        String value,
        Boolean temporary
) {
}
