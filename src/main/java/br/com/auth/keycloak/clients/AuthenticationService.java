package br.com.auth.keycloak.clients;

import br.com.auth.keycloak.clients.dtos.AuthorisationClientDataDTO;
import br.com.auth.keycloak.clients.dtos.UserDTO;
import io.smallrye.mutiny.Uni;

public interface AuthenticationService {

    Uni<AuthorisationClientDataDTO> login(String login, String password);

    Uni<Void> createUser(UserDTO userDTO);
}
