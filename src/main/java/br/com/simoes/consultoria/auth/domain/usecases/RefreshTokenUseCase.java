package br.com.simoes.consultoria.auth.domain.usecases;

import br.com.simoes.consultoria.auth.domain.entities.Authorization;
import io.smallrye.mutiny.Uni;

public interface RefreshTokenUseCase {

    Uni<Authorization> refresh(String refreshToken);
}
