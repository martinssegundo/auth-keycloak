package br.com.simoes.consultoria.auth.mappers;

import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.domain.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User converToUser(UserDTO userDTO);
    UserDTO converToUserDTO(User user);

}
