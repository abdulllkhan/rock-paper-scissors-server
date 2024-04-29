package com.project.stone.messages.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.stone.messages.dto.SendMesssagePayload;

@Service
@Component
public interface MessageService {

    String fetchNewMessages(String sessionCode, String username);

    String sendMessage(SendMesssagePayload sendMesssagePayload);
    
}
