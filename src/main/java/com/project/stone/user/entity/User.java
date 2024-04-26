package com.project.stone.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    // @Column(name = "email", nullable = false, length = 100)
    // private String email;

    @Column(name = "digest", nullable = false, length = 512)
    private String digest;

    @Column(name = "salt", nullable = false, length = 50)
    private String salt;

    @Column(name = "created_at")
    private Long createdAt;

    
    
}
