package com.project.stone.messages.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "message", length = 512, nullable = false)
    private String message;

    @Column(name = "session_code", length = 255, nullable = false)
    private String sessionCode;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

}
