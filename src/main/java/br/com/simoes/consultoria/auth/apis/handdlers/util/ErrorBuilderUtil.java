package br.com.simoes.consultoria.auth.apis.handdlers.util;

import br.com.simoes.consultoria.auth.apis.dtos.response.ErrorDTO;

import java.time.LocalDateTime;

public class ErrorBuilderUtil {

    private ErrorBuilderUtil(){}

    public static ErrorDTO buildError(Integer code, String message){
        return ErrorDTO.builder()
                .dateTimeError(LocalDateTime.now())
                .message(message)
                .status(code)
                .build();
    }
}
