package br.com.auth.keycloak.configs;

import br.com.auth.keycloak.clients.AuthenticationService;
import br.com.auth.keycloak.clients.UserManagerService;
import br.com.auth.keycloak.domain.usecases.impl.CreateUserKeycloakUseCase;
import br.com.auth.keycloak.mappers.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class
CreateUserProducer {

    @Produces
    @QualifierCA("loginUseCaseKeycloak")
    public CreateUserKeycloakUseCase createUserUseCaseKeycloak(UserManagerService userManagerService,
                                                               UserMapper userMapper){
        return new CreateUserKeycloakUseCase(userManagerService,userMapper);
    }

}
