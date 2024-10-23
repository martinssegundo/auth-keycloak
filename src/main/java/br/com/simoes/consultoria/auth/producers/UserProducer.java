package br.com.simoes.consultoria.auth.producers;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.FindUserUseCase;
import br.com.simoes.consultoria.auth.domain.usecases.impl.CreateUserUseCaseKeycloak;
import br.com.simoes.consultoria.auth.domain.usecases.impl.FindUserUseCaseKeyCloak;
import br.com.simoes.consultoria.auth.mappers.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class UserProducer {

    @Produces
    @QualifierCA("createUserUseCaseKeycloak")
    public CreateUserUseCaseKeycloak createUserUseCaseKeycloak(UserManagerService userManagerService,
                                                               UserMapper userMapper) {
        return new CreateUserUseCaseKeycloak(userManagerService, userMapper);
    }

    @Produces
    @QualifierCA("findUserUseCaseKeyCloak")
    public FindUserUseCase findUserUseCaseCaseKeycloak(UserManagerService userManagerService,
                                                     UserMapper userMapper) {
        return new FindUserUseCaseKeyCloak(userManagerService, userMapper);
    }

}
