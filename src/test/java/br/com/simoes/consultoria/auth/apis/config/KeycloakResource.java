package br.com.simoes.consultoria.auth.apis.config;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

public class KeycloakResource implements QuarkusTestResourceLifecycleManager {

    public static KeycloakContainer keycloak;

    @Override
    public Map<String, String> start() {
        keycloak = new KeycloakContainer()
                .withRealmImportFile("/jsons/authentication.json");
        keycloak.start();

        System.setProperty("quarkus.keycloak.admin-client.admin-user", "luiz");
        System.setProperty("quarkus.keycloak.admin-client.admin-password", "123456");
        System.setProperty("quarkus.keycloak.admin-client.realm", "construction");
        System.setProperty("quarkus.keycloak.admin-client.url", keycloak.getAuthServerUrl());
        System.setProperty("keycloak-login-api/mp-rest/url", keycloak.getAuthServerUrl() + "/realms/construction/protocol/openid-connect");
        System.setProperty("quarkus.keycloak.admin-client.server-url", keycloak.getAuthServerUrl() + "/realms/construction");
        System.setProperty("quarkus.oidc.auth-server-url", keycloak.getAuthServerUrl() + "/realms/construction");


        return Map.copyOf(buildKeyCloakProperties());
    }


    private Map<String, String> buildKeyCloakProperties(){


        return Map.of(
                "quarkus.keycloak.admin-client.client-id",  "auth-quarkus",
                "quarkus.keycloak.admin-client.client-secret", "mZLnOSxiF4nC46utkA4S1F3K3jIDIoaL",
                "quarkus.oidc.client-id",  "auth-quarkus",
                "quarkus.oidc.credentials.secret", "mZLnOSxiF4nC46utkA4S1F3K3jIDIoaL",

                "quarkus.keycloak.admin-client.url", keycloak.getAuthServerUrl(),
                "keycloak-login-api/mp-rest/url", keycloak.getAuthServerUrl() + "/realms/construction/protocol/openid-connect",
                "quarkus.keycloak.admin-client.server-url", keycloak.getAuthServerUrl() + "/realms/construction",
                "quarkus.oidc.auth-server-url", keycloak.getAuthServerUrl() + "/realms/construction"
        );
    }

    @Override
    public void stop() {
        if (keycloak != null) {
            keycloak.stop();
        }
    }
}
