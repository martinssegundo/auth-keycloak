package br.com.simoes.consultoria.auth.clients;

import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import io.smallrye.mutiny.Uni;

public interface UserManagerService {

    Uni<Void> createUser(UserDTO userDTO);
}
