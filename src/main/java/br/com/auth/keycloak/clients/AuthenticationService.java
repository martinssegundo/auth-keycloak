package br.com.auth.keycloak.clients;

import br.com.auth.keycloak.clients.dtos.AuthorisationClientDataDTO;
import io.smallrye.mutiny.Uni;

public interface AuthenticationService {

    Uni<AuthorisationClientDataDTO> login(String login, String password);
}
