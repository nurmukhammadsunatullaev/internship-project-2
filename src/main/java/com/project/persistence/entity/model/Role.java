package com.project.persistence.entity.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    DIRECTOR,
    TEACHER,
    RECEPTIONIST,
    STUDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
