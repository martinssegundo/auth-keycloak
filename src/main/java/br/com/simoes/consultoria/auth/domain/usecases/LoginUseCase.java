package br.com.simoes.consultoria.auth.domain.usecases;

import br.com.simoes.consultoria.auth.domain.entities.Authorization;
import br.com.simoes.consultoria.auth.domain.entities.Login;
import io.smallrye.mutiny.Uni;

public interface LoginUseCase {
    Uni<Authorization> login(Login login);
}
