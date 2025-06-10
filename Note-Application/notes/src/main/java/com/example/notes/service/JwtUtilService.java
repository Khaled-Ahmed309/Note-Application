package com.example.notes.service;

import com.example.notes.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtUtilService {
    private final String secretKey ;
    public JwtUtilService(){
        try {
            KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk=keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException();
        }
    }



    public String generateToken(UserEntity user){

            Map<String,Object> claims=new HashMap<>();
            claims.put("name",user.getName());
            claims.put("email",user.getEmail());
            claims.put("role",user.getRoles().getRole_name());

        return Jwts.builder()
                .claims(claims)                                                  //payloads
                .subject(user.getEmail())                                        //payloads
                .issuedAt(new Date(System.currentTimeMillis()))                  //payloads
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))  //payloads
                .signWith(getKey() ) //Signature
                .compact();
    }

    //Generate the key and put it in signature

    private SecretKey getKey(){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);

    }
    public String extractUserRole(String token){
        return extractClaim(token,claims -> claims.get("role",String.class));
    }
    private <T> T extractClaim(String token, Function<Claims,T>claimResolver){
        final Claims claims=extractAllClaim(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaim(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean validateToken(String token,UserEntity user){
        final String userName=extractUserName(token);
        return (userName.equals(user.getEmail())&&!isTokenExpired(token));
    }
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
