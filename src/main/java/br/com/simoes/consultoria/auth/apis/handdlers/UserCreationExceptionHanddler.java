package br.com.simoes.consultoria.auth.apis.handdlers;

import br.com.simoes.consultoria.auth.clients.exception.UserCreationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import static br.com.simoes.consultoria.auth.apis.handdlers.util.ErrorBuilderUtil.buildError;

public class UserCreationExceptionHanddler implements ExceptionMapper<UserCreationException> {
    @Override
    public Response toResponse(UserCreationException e) {
        return Response
                .status(e.getHttpStatus())
                .entity(buildError(e.getHttpStatus(),e.getMessage()))
                .build();
    }


}
