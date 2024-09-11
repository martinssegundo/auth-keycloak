package br.com.auth.keycloak.clients;

import br.com.auth.keycloak.clients.dtos.UserDTO;
import io.smallrye.mutiny.Uni;

public interface UserManagerService {

    Uni<Void> createUser(UserDTO userDTO);
}
