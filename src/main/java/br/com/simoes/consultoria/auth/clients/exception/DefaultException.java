package br.com.simoes.consultoria.auth.clients.exception;

import lombok.Data;

@Data
public class DefaultException extends RuntimeException {
    private int httpStatus;

    public DefaultException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
