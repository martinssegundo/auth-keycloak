package br.com.simoes.consultoria.auth.apis.config;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

public class KeycloakResource implements QuarkusTestResourceLifecycleManager {

    KeycloakContainer keycloak;

    @Override
    public Map<String, String> start() {
        KeycloakContainer keycloak = new KeycloakContainer()
                .withRealmImportFile("/jsons/authentication.json");
        keycloak.start();

        System.setProperty("quarkus.keycloak.admin-client.url", keycloak.getAuthServerUrl());
        System.setProperty("keycloak-login-api/mp-rest/url", keycloak.getAuthServerUrl() + "/realms/construction/protocol/openid-connect");
        System.setProperty("quarkus.keycloak.admin-client.server-url", keycloak.getAuthServerUrl() + "/realms/construction");
        System.setProperty("quarkus.oidc.auth-server-url", keycloak.getAuthServerUrl() + "/realms/construction");

        return Map.of(
                "quarkus.oidc.auth-server-url", keycloak.getAuthServerUrl() + "realms/quarkus",
                "quarkus.oidc.credentials.secret", "mZLnOSxiF4nC46utkA4S1F3K3jIDIoaL"
        );
    }

    @Override
    public void stop() {
        if (keycloak != null) {
            keycloak.stop();
        }
    }
}
