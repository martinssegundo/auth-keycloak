package br.com.simoes.consultoria.auth.clients.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.FederatedIdentityRepresentation;
import org.keycloak.representations.idm.UserConsentRepresentation;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Builder
public record UserDTO (
        @JsonProperty("access")
        Map<String, Object> access,

        @JsonProperty("attributes")
        Map<String, String> attributes,

        @JsonProperty("clientConsents")
        List<UserConsentRepresentation> clientConsents,

        @JsonProperty("clientRoles")
        Map<String, List<String>> clientRoles,

        @JsonProperty("createdTimestamp")
        Long createdTimestamp,

        @JsonProperty("credentials")
        List<CredentialRepresentation> credentials,

        @JsonProperty("disableableCredentialTypes")
        List<String> disableableCredentialTypes,

        @JsonProperty("email")
        String email,

        @JsonProperty("emailVerified")
        Boolean emailVerified,

        @JsonProperty("enabled")
        Boolean enabled,

        @JsonProperty("federatedIdentities")
        List<FederatedIdentityRepresentation> federatedIdentities,

        @JsonProperty("federationLink")
        String federationLink,

        @JsonProperty("firstName")
        String firstName,

        @JsonProperty("groups")
        List<String> groups,

        @JsonProperty("id")
        String id,

        @JsonProperty("lastName")
        String lastName,

        @JsonProperty("notBefore")
        Integer notBefore,

        @JsonProperty("origin")
        String origin,

        @JsonProperty("realmRoles")
        List<String> realmRoles,

        @JsonProperty("requiredActions")
        List<String> requiredActions,

        @JsonProperty("self")
        String self,

        @JsonProperty("serviceAccountClientId")
        String serviceAccountClientId,

        @JsonProperty("username")
        String username
) {
        public static UserDTO disableUser(UserDTO user) {
                return UserDTO.builder()
                        .access(user.access)
                        .attributes(user.attributes)
                        .clientConsents(user.clientConsents)
                        .clientRoles(user.clientRoles)
                        .createdTimestamp(user.createdTimestamp)
                        .credentials(user.credentials)
                        .disableableCredentialTypes(user.disableableCredentialTypes)
                        .email(user.email)
                        .emailVerified(user.emailVerified)
                        .enabled(false)
                        .federatedIdentities(user.federatedIdentities)
                        .federationLink(user.federationLink)
                        .firstName(user.firstName)
                        .groups(user.groups)
                        .id(user.id)
                        .lastName(user.lastName)
                        .notBefore(user.notBefore)
                        .origin(user.origin)
                        .realmRoles(user.realmRoles)
                        .requiredActions(user.requiredActions)
                        .self(user.self)
                        .serviceAccountClientId(user.serviceAccountClientId)
                        .username(user.username)
                        .build();
        }
}
