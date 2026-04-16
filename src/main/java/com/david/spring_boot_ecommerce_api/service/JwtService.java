package com.david.spring_boot_ecommerce_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

// Service responsável por tudo que envolve JWT:
// criar token, ler token e validar token.
@Service
public class JwtService {
    // Lê do application.properties a chave secreta usada para assinar o token
    @Value("${jwt.secret}")
    private String secretKey;

    // Lê do application.properties o tempo de expiração do token
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey getSigningKey() {
        // A secret vem como String (Base64), mas o JWT precisa de uma chave criptográfica real.
        // Aqui a String é convertida para bytes e depois para SecretKey.
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Cria um token JWT contendo:
    // subject = username
    // data de criação
    // data de expiração
    // assinatura usando a secret key
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    // Extrai o "subject" do token, que no nosso caso é o username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Token é válido se:
    // o username extraído for o mesmo
    // o token ainda não estiver expirado
    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    // Compara a data de expiração do token com a data atual
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Faz o parse do token, valida a assinatura e devolve o payload (claims)
    // Claims = dados armazenados dentro do token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
