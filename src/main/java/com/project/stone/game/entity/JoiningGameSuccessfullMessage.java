package com.project.stone.game.entity;

public class JoiningGameSuccessfullMessage {

    private Integer roomId;
    private String message;
    private String sessionCode;
    private Integer opponentId;

    public JoiningGameSuccessfullMessage(Integer roomId, String message,String sessionCode, Integer opponentId) {
        this.message = message;
        this.roomId = roomId;
        this.sessionCode = sessionCode;
        this.opponentId = opponentId;
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

    public Integer getOpponentId() {
        return opponentId;
    }
    
}
