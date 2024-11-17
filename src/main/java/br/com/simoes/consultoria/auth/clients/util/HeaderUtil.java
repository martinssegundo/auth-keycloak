package br.com.simoes.consultoria.auth.clients.util;

public class HeaderUtil {

    private static final String BEARER = "Bearer ";

    public static String bearrer(String token){
        return BEARER+token;
    }
}
