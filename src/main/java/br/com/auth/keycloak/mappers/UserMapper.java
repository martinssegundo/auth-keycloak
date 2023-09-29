package br.com.auth.keycloak.mappers;

import br.com.auth.keycloak.clients.dtos.UserDTO;
import br.com.auth.keycloak.domain.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User converToUser(UserDTO userDTO);
    UserDTO converToUserDTO(User user);

}
