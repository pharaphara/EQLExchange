package fr.eql.al36.spring.projet.eqlexchange.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class AuthTokenFilter extends GenericFilterBean {

    private final UserDetailsService customUserDetailsService;
    private static final String AUTH_TOKEN_HEADER_NAME = "Authorization";

    public AuthTokenFilter(UserDetailsService userDetailsService) {
        this.customUserDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authToken = httpServletRequest.getHeader(AUTH_TOKEN_HEADER_NAME);

            if (StringUtils.hasText(authToken)) {
                String username = JwtUtil.getUserNameFromToken(authToken);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (JwtUtil.validateToken(authToken, userDetails)) {

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
                            userDetails.getPassword(), userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
