package com.project.stone.game.dto;

public class SuccessfulRoomCreationMessage {

    private Integer roomId;
    private String message;
    private String sessionCode;

    public SuccessfulRoomCreationMessage(Integer roomId, String message,String sessionCode) {
        this.message = message;
        this.roomId = roomId;
        this.sessionCode = sessionCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getRoomId() {
        return roomId;
    }
    
    public String getSessionCode() {
        return sessionCode;
    }
}
