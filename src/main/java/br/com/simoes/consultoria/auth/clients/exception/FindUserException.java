package br.com.simoes.consultoria.auth.clients.exception;

public class FindUserException extends DefaultException {
    public FindUserException(String message, int httpStatus) {
        super(message, httpStatus);
    }
}
