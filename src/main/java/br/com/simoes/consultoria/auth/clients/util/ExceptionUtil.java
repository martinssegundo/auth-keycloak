package br.com.simoes.consultoria.auth.clients.util;

import jakarta.ws.rs.core.Response;

public class ExceptionUtil {


    public static String buildErrorMessage(String method, Response response, String localizedMessage){
        return String.format("Error when doing %s " +
                        "Status: %d " +
                        "message: %s ",
                method,
                response.getStatus(),
                localizedMessage);
    }

    public static String buildErrorMessage(String method, String login, Response response, String localizedMessage){
        return String.format("Error when doing %s " +
                        "Status: %d " +
                        "message: %s " +
                        "User: %s",
                method,
                response.getStatus(),
                localizedMessage,
                login);
    }


    public static String buildErrorMessage(String method, String login, String localizedMessage){
        return String.format("Error when doing %s " +
                        "message: %s " +
                        "User: %s",
                method,
                localizedMessage,
                login);
    }
}
