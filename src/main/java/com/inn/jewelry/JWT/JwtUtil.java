package com.inn.jewelry.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

/**
 The JwtUtil class is a service class for managing JSON Web Tokens (JWT).
 It provides methods for extracting information from a JWT token such as the subject (username),
 expiration date and all claims.
 */
@Service
public class JwtUtil {
    private String secret = "managementsystem";

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
}
