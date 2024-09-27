package br.com.simoes.consultoria.auth.domain.utils;

import jakarta.ws.rs.core.Form;

import java.util.Base64;

public class AuthenticationUtil {

    public static String buildBasic(String user, String password){
        var toEncrip = user+":"+password;
        return Base64.getEncoder().encodeToString(toEncrip.getBytes());
    }

    public static Form buildForm(String grantType, String user, String password){
        Form form = new Form();
        form.param("grant_type", grantType);
        form.param("username", user);
        form.param("password", password);
        form.param("client_id", "auth-quarkus");
        form.param("client_secret", "gOIEQnJuSCwp6amM9jK04Ml7XGznxUVx");
        return form;
    }
}
