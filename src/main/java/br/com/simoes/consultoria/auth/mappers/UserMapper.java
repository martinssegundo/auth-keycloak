package br.com.simoes.consultoria.auth.mappers;

import br.com.simoes.consultoria.auth.apis.dtos.request.UserCreateDTO;
import br.com.simoes.consultoria.auth.apis.dtos.response.UserResponseDTO;
import br.com.simoes.consultoria.auth.clients.dtos.UserDTO;
import br.com.simoes.consultoria.auth.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {

    User converToUser(UserCreateDTO userDTO);
    @Mapping(source = "id", target = "userIdentifier")
    User converToUser(UserDTO userDTO);
    UserResponseDTO convertToResponse(User user);
    @Mapping(source = "userIdentifier", target = "id")
    UserDTO converToUserDTO(User user);

    List<User> convertDtoListToEntityList(List<UserDTO> dtos);
    List<UserDTO> convertEntityListToDtoList(List<User> entities);
    List<UserResponseDTO> convertEntityListToResponseList(List<User> user);

}
