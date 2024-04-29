package com.project.stone.messages.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FetchMessagesDTO {

    private Boolean hasMessages;

    private Integer messageCount;

    private ArrayList<MessageDetails> messages = new ArrayList<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class MessageDetails{

        private String message;

        private String sender;
        
        private Long createdAt;


    }


    
}
