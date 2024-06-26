package com.project.stone.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Builder.Default;

@Getter
@Setter
@Validated    
@Valid  
public class CreateUserDTO {

    @NotBlank
    @Default
    private String username;

    @NotBlank
    private String hashedPassword;
    
}
