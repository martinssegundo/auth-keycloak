package br.com.auth.keycloak.mappers;

import br.com.auth.keycloak.apis.dtos.AuthorisationDataDTO;
import br.com.auth.keycloak.apis.dtos.LoginDataDTO;
import br.com.auth.keycloak.clients.dtos.AuthorisationClientDataDTO;
import br.com.auth.keycloak.domain.entities.Authorization;
import br.com.auth.keycloak.domain.entities.Login;
import org.mapstruct.Mapper;

@Mapper
public interface AuthenticationMapper {

    AuthorisationDataDTO convertToAuthorisationClientDataDTO(Authorization authorization);
    Authorization convertToAuthorization(AuthorisationClientDataDTO dto);
    Login convertToLogin(LoginDataDTO loginDataDTO);
}
