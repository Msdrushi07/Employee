package com.employee.filter;



import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import com.employee.jwt.jwtService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private jwtService jwtUtil;

    @Autowired
    private UserDetailsService service;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    // List of endpoints to skip JWT validation
    private static final String[] PUBLIC_URLS = {
        "/auth/**",
        "/public/**",
        "/health",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/v3/api-docs.yaml", 
        "/actuator/**",
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip filtering for public endpoints
        for (String publicUrl : PUBLIC_URLS) {
            if (pathMatcher.match(publicUrl, path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Missing Authorization header\"}");
            return;
        }
        
        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = jwtUtil.extractUsername(token);
        }
        log.info(token);
        log.info(userName);
        UserDetails userDetails= service.loadUserByUsername(userName);
        try {
        if(token != null) {
        	if( jwtUtil.validateToken(token, userDetails)) {
        	    UsernamePasswordAuthenticationToken auth =
        	        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        	    SecurityContextHolder.getContext().setAuthentication(auth);
        	}

        }
        }
        catch (Exception e) {
            System.out.println("invalid access...!");
            throw new RuntimeException("un authorized access to application");
        }
        filterChain.doFilter(request, response);
        }
}

        
 


