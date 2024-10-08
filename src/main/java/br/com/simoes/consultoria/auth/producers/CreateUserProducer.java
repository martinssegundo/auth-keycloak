package br.com.simoes.consultoria.auth.producers;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.impl.CreateUserKeycloakUseCase;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class CreateUserProducer {

    @Produces
    @QualifierCA("createUserUseCaseKeycloak")
    public CreateUserKeycloakUseCase createUserUseCaseKeycloak(UserManagerService userManagerService,
                                                               UserMapper userMapper) {
        return new CreateUserKeycloakUseCase(userManagerService, userMapper);
    }

}
