package home.app.grpc.api.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {
    private final String token;
    private final UserDetails principle;

    public TokenBasedAuthentication(UserDetails principle, String token) {
        super(principle.getAuthorities());
        this.principle = principle;
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return principle.getAuthorities();
    }

    @Override
    public Object getPrincipal() {
        return principle;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
