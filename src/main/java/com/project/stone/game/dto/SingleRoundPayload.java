package com.project.stone.game.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleRoundPayload {

    private Integer round;

    private String sessionCode;

    private Integer userId;

    private String move;
    
}
