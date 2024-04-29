package com.project.stone.game.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "session_id", nullable = false)
    private Integer sessionId;
    
    @Column(name = "round_id")
    private Integer roundId;
    
    @Column(name = "winner_id")
    private Integer winnerId;

    @Column(name = "is_played")
    private Boolean isPlayed;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_choice")
    private String userChoice;

}