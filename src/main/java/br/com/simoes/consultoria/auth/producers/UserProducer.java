package br.com.simoes.consultoria.auth.producers;

import br.com.simoes.consultoria.auth.clients.UserManagerService;
import br.com.simoes.consultoria.auth.configs.QualifierCA;
import br.com.simoes.consultoria.auth.domain.usecases.FindUserByLoginOrNameOrEmailUseCase;
import br.com.simoes.consultoria.auth.domain.usecases.impl.CreateUserUseCaseKeycloak;
import br.com.simoes.consultoria.auth.domain.usecases.impl.FindUserByLoginOrNameOrEmailUseCaseKeyCloak;
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
    public FindUserByLoginOrNameOrEmailUseCase findUserUseCaseCaseKeycloak(UserManagerService userManagerService,
                                                                           UserMapper userMapper) {
        return new FindUserByLoginOrNameOrEmailUseCaseKeyCloak(userManagerService, userMapper);
    }

}
