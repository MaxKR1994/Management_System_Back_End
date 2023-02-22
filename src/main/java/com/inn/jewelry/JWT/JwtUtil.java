package com.inn.jewelry.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 The JwtUtil class is a service class for managing JSON Web Tokens (JWT).
 It provides methods for extracting information from a JWT token such as the subject (username),
 expiration date and all claims.
 */

/**

 TThe @Service annotation is used to indicate that a class is a Spring-managed service bean.
 It is used to indicate that the annotated class performs
 some business logic and is eligible for dependency injection.
 */
@Service
public class JwtUtil {
    private final String secret = "managementsystem";

    /**
     The extractUsername method takes a JWT token as input and returns the subject (username)
     contained in the token by using the extractClaims method.
     @param token the JWT token
     @return the subject (username) contained in the token
     */
    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    /**
     The extractExpiration method takes a JWT token as input and returns the expiration date
     contained in the token by using the extractClaims method.
     @param token the JWT token
     @return the expiration date contained in the token
     */
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    /**
     The extractClaims method takes a JWT token and a claimsResolver function as input,
     and returns the result of applying the claimsResolver function to the claims contained in the token.
     @param token the JWT token
     @param claimsResolver the function to apply to the claims in the token
     @return the result of applying the claimsResolver function to the claims in the token
     */
    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     The extractAllClaims method takes a JWT token as input and returns all the claims contained in the token.
     The method uses the Jwts.parser().setSigningKey method to parse the token and extract its claims.
     @param token the JWT token
     @return all the claims contained in the token
     */
    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**

     The isTokenExpired method takes a JWT token as input and returns whether the token has expired.
     @param token the JWT token
     @return true if the token has expired, false otherwise
     */
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    /**

     The generateToken method takes a username and role as input and returns a new JWT token containing the claims.
     @param username the username to include in the token
     @param role the role to include in the token
     @return the JWT token with the claims
     */
    public String generateToken(String username, String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims,username);
    }

    /**

     The createToken method takes claims and a subject as input and returns a new JWT token with the claims and subject.
     @param claims the claims to include in the token
     @param subject the subject to include in the token
     @return the JWT token with the claims and subject
     */
    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    /**

     Validates a JSON Web Token (JWT) by checking if the provided UserDetails match the claims of the token.
     @param token the JWT to validate
     @param userDetails the UserDetails to match the token against
     @return true if the token is valid, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
