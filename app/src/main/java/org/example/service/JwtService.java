package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyAgreementSpi;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//It  store the token
@Service
public class JwtService {
//    We don't store this file as it is we store in key manger
    public static  final String SECRET ="3f4a1c8b9e0f63a7bc5e0b7d2acb8c6934d5e1f28d0c7a3f1b5d9283cbf76319";
//    JWT Claim(Object) is is basically data structure it store all information
    public  String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    public <T>  T extractClaim(String token, Function<Claims,T>claimResolver){
        final Claims claims=extractAllClaim(token);
        return  claimResolver.apply(claims);
    }
    public Date extractExpiration(String token){
        return  extractClaim(token,Claims::getExpiration);
    }
    private  boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }
    public Boolean validToken(String token, UserDetails userDetails){
        final  String username=extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    public String GenerateToken(String username){
    Map<String,Object>claims=new HashMap<>();
    return createToken(claims,username);
    }
    private  String createToken(Map<String,Object> claims,String username){
        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*1))
                .signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
    }

    private  Claims extractAllClaim(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
    byte[] keyBytes= Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
    }

}

