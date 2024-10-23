package br.com.simoes.consultoria.auth.mappers;

import br.com.simoes.consultoria.auth.apis.dtos.response.AuthorisationDataDTO;
import br.com.simoes.consultoria.auth.apis.dtos.request.LoginDataDTO;
import br.com.simoes.consultoria.auth.clients.dtos.AuthorisationClientDataDTO;
import br.com.simoes.consultoria.auth.domain.entities.Authorization;
import br.com.simoes.consultoria.auth.domain.entities.Login;
import org.mapstruct.Mapper;

@Mapper
public interface AuthenticationMapper {

    AuthorisationDataDTO convertToAuthorisationClientDataDTO(Authorization authorization);
    Authorization convertToAuthorization(AuthorisationClientDataDTO dto);
    Login convertToLogin(LoginDataDTO loginDataDTO);
}
