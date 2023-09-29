package br.com.auth.keycloak.configs;

import br.com.auth.keycloak.clients.AuthenticationService;
import br.com.auth.keycloak.domain.usecases.impl.CreateUserUseCaseKeycloak;
import br.com.auth.keycloak.mappers.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class CreateUserProducer {

    @Produces
    @ApplicationScoped
    @Named("loginUseCaseKeycloak")
    public CreateUserUseCaseKeycloak createUserUseCaseKeycloak(AuthenticationService authenticationService,
                                                               UserMapper userMapper){
        return new CreateUserUseCaseKeycloak(authenticationService,userMapper);
    }

}
