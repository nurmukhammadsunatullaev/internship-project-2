package com.project.persistence.entity;

import com.project.persistence.entity.model.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credentials")
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private Role role;

    public Credentials(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
