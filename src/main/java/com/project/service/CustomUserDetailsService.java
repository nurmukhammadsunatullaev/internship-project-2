package com.project.service;

import com.project.exception.codes.CredentialsExceptionCode;
import com.project.exception.entity.CredentialsException;
import com.project.persistence.entity.Credentials;
import com.project.persistence.repository.AuthRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService {

    private final AuthRepository repository;

    public CustomUserDetailsService(AuthRepository repository) {
        this.repository = repository;
    }

    public UserDetailsService getDetails() {
        return login -> {
            List<GrantedAuthority> authorities = new ArrayList<>();
            Credentials user = repository.findByLogin(login).orElseThrow(
                    () -> new CredentialsException(CredentialsExceptionCode.USER_NOT_FOUND));
            authorities.add(user.getRole());
            return new User(user.getLogin(), user.getPassword(), authorities);
        };
    }
}
