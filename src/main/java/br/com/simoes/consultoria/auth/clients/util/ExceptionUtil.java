package br.com.simoes.consultoria.auth.clients.util;

import jakarta.ws.rs.core.Response;

public class ExceptionUtil {

    public static String buildErrorMessage(String method, String login, Response response, String localizedMessage){
        return String.format("{} ==============> " +
                        "Status: %d " +
                        "message: %s " +
                        "User: %s",
                method,
                response.getStatus(),
                localizedMessage,
                login);
    }


    public static String buildErrorMessage(String method, String login, String localizedMessage){
        return String.format("{} ==============> " +
                        "message: %s " +
                        "User: %s",
                method,
                localizedMessage,
                login);
    }
}
