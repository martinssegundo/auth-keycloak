package br.com.simoes.consultoria.auth.domain.utils;

import jakarta.ws.rs.core.Form;

import java.util.Base64;

public class AuthenticationUtil {

    private static final String GRANT_TYPE = "grant_type";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REFRESH_TOKEN = "refresh_token";


    public static String buildBasic(String user, String password) {
        var toEncrip = user + ":" + password;
        return Base64.getEncoder().encodeToString(toEncrip.getBytes());
    }

    public static Form buildFormLogin(String grantType,
                                      String user,
                                      String password,
                                      String clientId,
                                      String clientSecret) {
        Form form = new Form();
        form.param(GRANT_TYPE, grantType);
        form.param(USERNAME, user);
        form.param(PASSWORD, password);
        form.param(CLIENT_ID, clientId);
        form.param(CLIENT_SECRET, clientSecret);
        return form;
    }


    public static Form buildFormRefreshToken(String grantType,
                                             String clientId,
                                             String clientSecret,
                                             String refreshToken) {
        Form form = new Form();
        form.param(GRANT_TYPE, grantType);
        form.param(CLIENT_ID, clientId);
        form.param(CLIENT_SECRET, clientSecret);
        form.param(REFRESH_TOKEN, refreshToken);
        return form;
    }
}
