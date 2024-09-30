package br.com.simoes.consultoria.auth.clients.exception;

public class AuthenticationException extends DefaultException {

    public AuthenticationException(Integer httpStatus, String message){
        super(message,httpStatus);
    }
}
