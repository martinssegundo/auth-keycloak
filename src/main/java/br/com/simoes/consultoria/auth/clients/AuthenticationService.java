package br.com.simoes.consultoria.auth.clients;

import br.com.simoes.consultoria.auth.clients.dtos.AuthorisationClientDataDTO;
import io.smallrye.mutiny.Uni;

public interface AuthenticationService {

    Uni<AuthorisationClientDataDTO> login(String login, String password);

}
