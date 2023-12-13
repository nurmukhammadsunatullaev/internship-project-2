package com.project.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationDate;

    @OneToOne
    @JoinColumn(name = "credentials_id", referencedColumnName = "id")
    private Credentials credentials;

    public RefreshToken(String token,Date expirationDate, Credentials credentials) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.credentials = credentials;
    }
}
