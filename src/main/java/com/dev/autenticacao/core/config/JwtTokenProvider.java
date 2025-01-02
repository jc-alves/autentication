package com.dev.autenticacao.core.config;

import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;
import com.dev.autenticacao.core.domain.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtTokenProvider {

//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.expiration}")
//    private long expiration;

    public String generateToken(String user) {
        return "dsfasdfasdfasdfasdfasd";
//                Jwts.builder()
//                .setSubject(user.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
    }
}
