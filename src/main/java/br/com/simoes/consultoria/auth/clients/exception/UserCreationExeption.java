package br.com.simoes.consultoria.auth.clients.exception;

import lombok.Data;

@Data
public class UserCreationExeption extends RuntimeException {
    private int httpStatus;
    private String message;

    public UserCreationExeption(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
