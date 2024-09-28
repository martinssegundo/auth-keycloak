package br.com.simoes.consultoria.auth.domain.entities;

import br.com.simoes.consultoria.auth.domain.entities.builds.AuthorizationBuilder;

import java.util.Objects;

public class Authorization {
    private String accessToken;
    private Long expiresIn;
    private Long refreshExpiresIn;
    private String refreshToken;
    private String tokenType;
    private Long notBeforePolicy;
    private String sessionState;
    private String scope;

    public Authorization(){}

    public Authorization(String accessToken, Long expiresIn, Long refreshExpiresIn,
                         String refreshToken, String tokenType, Long notBeforePolicy,
                         String sessionState, String scope) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshExpiresIn = refreshExpiresIn;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.notBeforePolicy = notBeforePolicy;
        this.sessionState = sessionState;
        this.scope = scope;
    }

    public static AuthorizationBuilder builder() {
        return AuthorizationBuilder.getInstance();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(Long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getNotBeforePolicy() {
        return notBeforePolicy;
    }

    public void setNotBeforePolicy(Long notBeforePolicy) {
        this.notBeforePolicy = notBeforePolicy;
    }

    public String getSessionState() {
        return sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authorization that = (Authorization) o;
        return Objects.equals(accessToken, that.accessToken) && Objects.equals(expiresIn, that.expiresIn) && Objects.equals(refreshExpiresIn, that.refreshExpiresIn) && Objects.equals(refreshToken, that.refreshToken) && Objects.equals(tokenType, that.tokenType) && Objects.equals(notBeforePolicy, that.notBeforePolicy) && Objects.equals(sessionState, that.sessionState) && Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, expiresIn, refreshExpiresIn, refreshToken, tokenType, notBeforePolicy, sessionState, scope);
    }
}
