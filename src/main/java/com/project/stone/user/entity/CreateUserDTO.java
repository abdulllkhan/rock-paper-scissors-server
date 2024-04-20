package com.project.stone.user.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;
    
}
