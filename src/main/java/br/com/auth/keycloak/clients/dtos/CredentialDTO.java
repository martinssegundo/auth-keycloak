package br.com.auth.keycloak.clients.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialDTO {
    private String type;
    private String value;
    private boolean temporary;
}
