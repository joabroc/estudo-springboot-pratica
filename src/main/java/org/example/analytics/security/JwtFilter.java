package org.example.analytics.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.analytics.service.CustomUserDetailsService;
import org.example.analytics.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Sem token: 403 Forbidden
            response.setStatus(403);
            response.getWriter().write("Forbidden: Token required");
            log.debug("event=filter.call entity=jwt operation=validateToken outcome=failure reason=no_token");
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("event=filter.call entity=jwt operation=validateToken outcome=start username={}", userEmail);
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("event=filter.call entity=jwt operation=validateToken outcome=success username={}", userEmail);
                } else {
                    // Token inválido: deixar o Spring Security lançar 401
                    log.debug("event=filter.call entity=jwt operation=validateToken outcome=failure reason=invalid_token username={}", userEmail);
                }
            } catch (Exception e) {
                // Erro ao carregar usuário ou validar: 401
                log.debug("event=filter.call entity=jwt operation=validateToken outcome=failure reason=exception username={} error={}", userEmail, e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
