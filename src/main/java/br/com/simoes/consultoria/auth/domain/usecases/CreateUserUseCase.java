package br.com.simoes.consultoria.auth.domain.usecases;

import br.com.simoes.consultoria.auth.domain.entities.User;
import io.smallrye.mutiny.Uni;

public interface CreateUserUseCase {

    Uni<Void> createUser(User user);

}
