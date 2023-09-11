package br.com.auth.keycloak.mappers;

import br.com.auth.keycloak.clients.dtos.AuthorisationDataDTO;
import br.com.auth.keycloak.domain.entities.Authorization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface AuthenticationMapper {

    Authorization convertToAuthorization(AuthorisationDataDTO dto);
}
