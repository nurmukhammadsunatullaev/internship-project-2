package com.project.persistence.dto.request;

import com.project.persistence.entity.model.Role;

public record RegistrationRequest(String login, String password, Role role) {
}
