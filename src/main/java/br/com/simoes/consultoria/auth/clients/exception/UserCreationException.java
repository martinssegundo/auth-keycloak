package br.com.simoes.consultoria.auth.clients.exception;

import lombok.Data;

public class UserCreationException extends DefaultException {
    private int httpStatus;

    public UserCreationException(String message, int httpStatus) {
        super(message,httpStatus);
    }
}
