package com.project.stone.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMesssagePayload {

    private String sender;

    private String message;
    
    private String sessionCode;
    
}
