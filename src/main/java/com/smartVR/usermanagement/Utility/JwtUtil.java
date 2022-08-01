package com.smartVR.usermanagement.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final long JWT_TOKEN_VALIDITY = 5*60*60;

    private String secret = "jwtTokenKey";

    //Retrieve Username from Token
    public String getUserNameFromToken(String token){
        return extractClaims(token, Claims::getSubject);
    }

    //Retrieve the Expiration Date of token
    public Date getExpiryDate(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Retrieving any Information from token we need
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //Check if the Token has Expired
    public Boolean isTokenExpired(String token){
        final Date expiration = getExpiryDate(token);
        return expiration.before(new Date());
    }

    //Generate Token From User
    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<String,Object>();
        return createToken(claims,username);
    }

    //While Generating Token
    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*100))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    //Token Validation
    public Boolean validateToken(String token,UserDetails userDetails){
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }
}

