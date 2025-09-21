package ar.com.pichidev.homestock.common.configuration.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Map<String, Object> claims;

    public JwtAuthenticationToken(Map<String, Object> claims, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.claims = claims;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null; // no hay password
    }

    @Override
    public Object getPrincipal() {
        return claims; // o claims.get("sub") si querés
    }

}