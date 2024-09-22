package br.com.simoes.consultoria.auth.domain.entities.builds;

import br.com.simoes.consultoria.auth.domain.entities.Authorization;

public final class AuthorizationBuilder {
    private String accessToken;
    private Long expiresIn;
    private Long refreshExpiresIn;
    private String refreshToken;
    private String tokenType;
    private Long notBeforePolicy;
    private String sessionState;
    private String scope;

    private AuthorizationBuilder() {
    }

    public static AuthorizationBuilder getInstance() {
        return new AuthorizationBuilder();
    }

    public AuthorizationBuilder accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public AuthorizationBuilder expiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public AuthorizationBuilder refreshExpiresIn(Long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
        return this;
    }

    public AuthorizationBuilder refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public AuthorizationBuilder tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public AuthorizationBuilder notBeforePolicy(Long notBeforePolicy) {
        this.notBeforePolicy = notBeforePolicy;
        return this;
    }

    public AuthorizationBuilder sessionState(String sessionState) {
        this.sessionState = sessionState;
        return this;
    }

    public AuthorizationBuilder scope(String scope) {
        this.scope = scope;
        return this;
    }

    public Authorization build() {
        Authorization authorization = new Authorization();
        authorization.setAccessToken(accessToken);
        authorization.setExpiresIn(expiresIn);
        authorization.setRefreshExpiresIn(refreshExpiresIn);
        authorization.setRefreshToken(refreshToken);
        authorization.setTokenType(tokenType);
        authorization.setNotBeforePolicy(notBeforePolicy);
        authorization.setSessionState(sessionState);
        authorization.setScope(scope);
        return authorization;
    }
}
