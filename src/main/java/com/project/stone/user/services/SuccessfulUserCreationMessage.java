package com.project.stone.user.services;

public class SuccessfulUserCreationMessage {

    private String message;
    private Integer id; 

    public SuccessfulUserCreationMessage(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
