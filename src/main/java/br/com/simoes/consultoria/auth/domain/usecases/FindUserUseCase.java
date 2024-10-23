package br.com.simoes.consultoria.auth.domain.usecases;

import br.com.simoes.consultoria.auth.domain.entities.User;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface FindUserUseCase {

    Uni<List<User>> findUser(String username, Integer max, Integer page);
}
