package com.inn.jewelry.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**

 JwtFilter is responsible for filtering incoming requests, extracting and validating JWT tokens, and authenticating users.
 */

/**

 JThe @Component annotation is used to indicate that a class is a component or a bean in the Spring framework.
 When this annotation is added to a class, Spring's component scanning mechanism automatically detects and registers
 the class as a bean. The bean can then be used and injected into other parts of the application
 using Spring's dependency injection.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    /**

     The @Autowired annotation is used in Spring to automatically inject dependencies
     into a class. When a class has a field or constructor annotated with @Autowired,
     Spring will search for a bean of the corresponding type and automatically wire it into the class.
     This can greatly simplify dependency management and make the code more modular and easy to understand.
     */
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomerUserDetailsService service;

    Claims claims = null;
    private String userName = null;

    /**
     * This method extracts the JWT token from the request header, validates the token, and sets the user details in the security context.
     * @param httpServletRequest The HTTP request object.
     * @param httpServletResponse The HTTP response object.
     * @param filterChain The filter chain object.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getServletPath().matches("/user/login|/user/forgotPassword|/user/signup")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                userName = jwtUtil.extractUsername(token);
                claims = jwtUtil.extractAllClaims(token);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = service.loadUserByUsername(userName);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    /**
     * This method checks whether the current user is an admin.
     * @return True if the current user is an admin, false otherwise.
     */
    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    /**
     * This method checks whether the current user is a regular user.
     * @return True if the current user is a regular user, false otherwise.
     */
    public boolean isUser(){
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    /**
     * This method returns the username of the current user.
     * @return The username of the current user.
     */
    public String getCurrentUser(){
        return userName;
    }

}
