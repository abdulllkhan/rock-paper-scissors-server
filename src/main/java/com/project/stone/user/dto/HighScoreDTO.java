package com.project.stone.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighScoreDTO {

    private Integer highestScore;

    private Integer totalScores;

    private ScoreInfo[] highScores;


    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ScoreInfo{

        private Integer score;
        
        private String username;

    }
    
}
