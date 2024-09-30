package br.com.simoes.consultoria.auth.apis.handdlers;

import br.com.simoes.consultoria.auth.clients.exception.AuthenticationException;
import br.com.simoes.consultoria.auth.clients.exception.UserCreationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static br.com.simoes.consultoria.auth.apis.handdlers.util.ErrorBuilderUtil.buildError;

@Provider
public class AuthenticationExceptionHanddler  implements ExceptionMapper<AuthenticationException> {
    @Override
    public Response toResponse(AuthenticationException e) {
        return Response
                .status(e.getHttpStatus())
                .entity(buildError(e.getHttpStatus(),e.getMessage()))
                .build();
    }
}
