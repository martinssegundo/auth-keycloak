package br.com.simoes.consultoria.auth.clients.exception;

public class AuthenticationException extends DefaultException {

    public AuthenticationException(String message, Integer code){
        super(message,code);
    }
}
