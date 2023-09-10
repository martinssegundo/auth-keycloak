package br.com.auth.keycloak.domain.usecases;

import br.com.auth.keycloak.domain.entities.Authorization;
import br.com.auth.keycloak.domain.entities.Login;
import io.smallrye.mutiny.Uni;

public interface LoginUseCase {
    Uni<Authorization> login(Login login);
}
