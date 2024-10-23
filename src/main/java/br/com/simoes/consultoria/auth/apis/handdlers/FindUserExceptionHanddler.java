package br.com.simoes.consultoria.auth.apis.handdlers;

import br.com.simoes.consultoria.auth.clients.exception.FindUserException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import static br.com.simoes.consultoria.auth.apis.handdlers.util.ErrorBuilderUtil.buildError;

@Provider
@Slf4j
public class FindUserExceptionHanddler implements ExceptionMapper<FindUserException> {
    @Override
    public Response toResponse(FindUserException e) {
        log.info("Building response error: FindUserException");
        return Response
                .status(e.getHttpStatus())
                .entity(buildError(e.getHttpStatus(),e.getMessage()))
                .build();
    }
}
