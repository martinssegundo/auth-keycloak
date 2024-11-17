package br.com.simoes.consultoria.auth.clients;

import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface UserManagerService {

    Uni<Void> createUser(UserDTO userDTO);

    Uni<List<UserDTO>> findUserByNameOrFirstNameOrEmail(String username, Integer max, Integer page);

    Uni<List<UserDTO>> listAll(Integer max, Integer page);

    Uni<Void> disableUserByUserId(String userId);
}
