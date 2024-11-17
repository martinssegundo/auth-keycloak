package br.com.simoes.consultoria.auth.domain.usecases;

import br.com.simoes.consultoria.auth.domain.entities.User;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface FindUserByLoginOrNameOrEmailUseCase {

    Uni<List<User>> findUserByNameOrFirsNamOrEmail(String username, Integer max, Integer page);
}
