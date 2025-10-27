package ar.com.pichidev.template.common.configuration.jwt;

import ar.com.pichidev.piauth.standard.jwt.usecase.DecodeJwtUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class DecodeJwtFilter extends OncePerRequestFilter {

    private final DecodeJwtUseCase tokenDecoder; // tu librería

    public DecodeJwtFilter(DecodeJwtUseCase tokenDecoder) {
        this.tokenDecoder = tokenDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Map<String, Object> claims = tokenDecoder.execute(token);

                JwtAuthenticationToken auth = new JwtAuthenticationToken(claims, extractAuthorities(claims)); // sin roles
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private Collection<SimpleGrantedAuthority> extractAuthorities(Map<String, Object> claims) {
        List<String> roles = (List<String>) claims.getOrDefault("roles", List.of());
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }
}
