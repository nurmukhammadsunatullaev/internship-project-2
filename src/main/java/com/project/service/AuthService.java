package com.project.service;

import com.project.exception.codes.CredentialsExceptionCode;
import com.project.exception.codes.TokenExceptionCode;
import com.project.exception.entity.CredentialsException;
import com.project.exception.entity.TokenException;
import com.project.persistence.dto.request.LoginRequest;
import com.project.persistence.dto.request.RefreshRequest;
import com.project.persistence.dto.request.RegistrationRequest;
import com.project.persistence.dto.response.AuthResponse;
import com.project.persistence.dto.response.AuthTokenRefreshResponse;
import com.project.persistence.dto.response.MessageResponse;
import com.project.persistence.entity.Credentials;
import com.project.persistence.entity.RefreshToken;
import com.project.persistence.repository.AuthRepository;
import com.project.persistence.repository.RefreshTokenRepository;
import com.project.util.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private AuthenticationManager manager;
    private TokenGenerator generator;
    private AuthRepository authRepository;
    private RefreshTokenRepository refreshTokenRepository;
    private BCryptPasswordEncoder encoder;

    public AuthService(AuthenticationManager manager, TokenGenerator generator,
                       AuthRepository authRepository, RefreshTokenRepository refreshTokenRepository,
                       BCryptPasswordEncoder encoder) {
        this.manager = manager;
        this.generator = generator;
        this.authRepository = authRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.encoder = encoder;
    }

    public MessageResponse createUser(RegistrationRequest request) {
        if (authRepository.existsByLogin(request.login())) {
            throw new CredentialsException(CredentialsExceptionCode.LOGIN_IN_USE);
        } else {
            authRepository.save(new Credentials(request.login(), encoder.encode(request.password()), request.role()));
            return MessageResponse.builder()
                    .message("User successfully registered")
                    .build();
        }
    }

    public AuthResponse loginUser(LoginRequest request) throws CredentialsException {
        Credentials user = authRepository.findByLogin(request.login()).orElseThrow(
                () -> new CredentialsException(CredentialsExceptionCode.USER_NOT_FOUND));
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        RefreshToken refreshToken = generator.generateRefreshToken(authentication.getName());
        String accessToken = null;
        if (generator.verifyRefreshToken(refreshToken)) {
            accessToken = generator.generateAccessToken(authentication.getName());
        }
        AuthResponse responseDto = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
        if (accessToken == null) {
            throw new CredentialsException(CredentialsExceptionCode.USER_NOT_FOUND);
        }
        return responseDto;
    }

    public AuthTokenRefreshResponse refreshAccessToken(RefreshRequest request) {
        RefreshToken token = generator.findRefreshTokenEntityByTokenString(request.refreshToken());
        Credentials user = authRepository.findById(token.getCredentials().getId())
                .orElseThrow(() -> new CredentialsException(CredentialsExceptionCode.USER_NOT_FOUND));
        String accessToken = generator.generateAccessToken(user.getLogin());
        return AuthTokenRefreshResponse.builder().accessToken(accessToken).build();
    }

    public MessageResponse logoutUser(String token, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            request.getSession().invalidate();
        }
        User user = (User) authentication.getPrincipal();
        Credentials credentials = authRepository.findByLogin(user.getUsername())
                .orElseThrow(() -> new CredentialsException(CredentialsExceptionCode.USER_NOT_FOUND));
        RefreshToken refreshToken = refreshTokenRepository.findByCredentials_Id(credentials.getId())
                .orElseThrow(() -> new TokenException(TokenExceptionCode.REFRESH_TOKEN_EXPIRED));
        refreshTokenRepository.delete(refreshToken);
        return MessageResponse.builder()
                .message("You are successfully logged out")
                .build();

    }
}
