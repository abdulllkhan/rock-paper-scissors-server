package com.project.stone.game.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EndGameDTO {

    @NotBlank  
    private String sessionCode;
    
}
