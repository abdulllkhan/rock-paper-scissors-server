package com.project.stone.game.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameWinnerDetails {

    private String sessionCode;

    private Integer winnerId;

    private String winnerName;

    private Integer userId1;

    private Integer userId2;

    private String message;
    
}
