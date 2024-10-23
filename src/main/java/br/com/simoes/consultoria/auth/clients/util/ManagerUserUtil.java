package br.com.simoes.consultoria.auth.clients.util;

import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.clients.exception.UserCreationException;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;

import java.util.List;

import static br.com.simoes.consultoria.auth.clients.util.ExceptionUtil.buildErrorMessage;

@Slf4j
public class ManagerUserUtil {

    public static UserCreationException creationErrorBuilder(Throwable error, String user, String method) {
        var messageError = buildErrorMessage(
                method,
                user,
                error.getLocalizedMessage()
        );
        log.error(messageError);
        return new UserCreationException(messageError, HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
    }

    public static UserCreationException listErrorBuilder(Throwable error, String user, String method) {
        var messageError = buildErrorMessage(
                method,
                user,
                error.getLocalizedMessage()
        );
        log.error(messageError);
        return new UserCreationException(messageError, HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
    }


    public static UserDTO buildUserToFirstSave(UserDTO userDTO) {
        return UserDTO.builder()
                .username(userDTO.username())
                .email(userDTO.email())
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .enabled(Boolean.TRUE)
                .emailVerified(Boolean.FALSE)
                .credentials(List.of(buildFirstCredential()))
                .requiredActions(List.of())
                .groups(List.of())
                .build();
    }

    public static CredentialRepresentation buildFirstCredential() {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue("Us3rCr3d3nt14l");
        return credentialRepresentation;
    }
}
