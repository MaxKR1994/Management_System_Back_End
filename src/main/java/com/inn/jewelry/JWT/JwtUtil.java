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

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims,username);
    }

    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
