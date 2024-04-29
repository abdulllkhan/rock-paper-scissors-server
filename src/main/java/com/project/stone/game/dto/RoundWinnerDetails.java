package com.project.stone.game.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoundWinnerDetails {

    private String sessionCode;

    private Integer round;

    private Integer winnerId;

    private Integer userId1;

    private Integer userId2;

    private String userId1Choice;

    private String userId2Choice;
    
}
