package br.com.auth.keycloak.domain.usecases;

import br.com.auth.keycloak.domain.entities.User;
import io.smallrye.mutiny.Uni;

public interface CreateUserUseCase {

    Uni<Void> createUser(User user);

}
