package com.project.stone.game.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user1_id")
    private Integer user1Id;

    @Column(name = "user2_id")
    private Integer user2Id;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_vacant")
    private Boolean isVacant;

    @Column(name = "session_code")
    private String sessionCode;

    @Column(name = "winner_id")
    private Integer winnerId;

}
