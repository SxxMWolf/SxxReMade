package com.example.record.auth;

import com.example.record.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Profile("dev")
@Component
public class DevAuthBypassFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws java.io.IOException, jakarta.servlet.ServletException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            User fake = User.builder()
                    .id(0L).email("dev@local").nickname("DEV").password("N/A")
                    .role("USER")
                    .build();

            var auth = new UsernamePasswordAuthenticationToken(
                    fake, null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    // 필요 시 ADMIN 테스트: new SimpleGrantedAuthority("ROLE_ADMIN") 추가
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(req, res);
    }
}
