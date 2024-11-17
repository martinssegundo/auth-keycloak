package br.com.simoes.consultoria.auth.domain.usecases;

import io.smallrye.mutiny.Uni;

public interface DisableUserByUserIdUseCase {

    Uni<Void> disableUser(String userId);
}
