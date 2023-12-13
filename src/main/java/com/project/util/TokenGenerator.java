package com.project.util;

import com.project.exception.codes.CredentialsExceptionCode;
import com.project.exception.codes.TokenExceptionCode;
import com.project.exception.entity.CredentialsException;
import com.project.exception.entity.TokenException;
import com.project.persistence.entity.Credentials;
import com.project.persistence.entity.RefreshToken;
import com.project.persistence.repository.AuthRepository;
import com.project.persistence.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenGenerator {

    @Value("${jwt.expiration.time}")
    private int expirationTimeAccess;

    @Value("${refresh.expiration.time}")
    private int expirationTimeRefresh;

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String login) {
        Date current = new Date();
        Date expired = new Date(current.getTime() + expirationTimeAccess);
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(current)
                .setExpiration(expired)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new TokenException(TokenExceptionCode.ACCESS_TOKEN_EXPIRED);
        }
    }

    public String getParamFromAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public RefreshToken generateRefreshToken(String login) {
        Date current = new Date();
        Date expired = new Date(current.getTime() + expirationTimeRefresh);
        Credentials credentials = authRepository.findByLogin(login)
                .orElseThrow(() -> new CredentialsException(CredentialsExceptionCode.USER_NOT_FOUND));
        if (refreshTokenRepository.existsByCredentials_Id(credentials.getId())) {
            String token = UUID.randomUUID().toString();
            refreshTokenRepository.changeToken(credentials, token);
            return refreshTokenRepository.findByToken(token)
                    .orElseThrow(() -> new TokenException(TokenExceptionCode.REFRESH_TOKEN_EXPIRED));
        } else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .credentials(authRepository.findByLogin(login)
                            .orElseThrow(() -> new CredentialsException(CredentialsExceptionCode.USER_NOT_FOUND)))
                    .token(UUID.randomUUID().toString())
                    .expirationDate(expired)
                    .build();
            return refreshTokenRepository.save(refreshToken);
        }
    }

    public RefreshToken findRefreshTokenEntityByTokenString(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenException(TokenExceptionCode.REFRESH_TOKEN_EXPIRED));
    }

    public boolean verifyRefreshToken(RefreshToken token) {
        Date current = new Date();
        if (token.getExpirationDate().getTime() < current.getTime()) {
            refreshTokenRepository.delete(token);
            throw new TokenException(TokenExceptionCode.NEED_SIGN_IN);
        }
        return true;
    }
}
