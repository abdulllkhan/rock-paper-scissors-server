package com.project.stone.messages.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.project.stone.messages.dto.SendMesssagePayload;
import com.project.stone.messages.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@Validated
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("api/message/send")
    public String postMethodName(@RequestBody SendMesssagePayload sendMesssagePayload) {
        return messageService.sendMessage(sendMesssagePayload);
    }

    @GetMapping("api/message/fetch/{sessionCode}")
    public String getMethodName(@PathVariable String sessionCode, @RequestParam("username") String username) {
        return messageService.fetchNewMessages(sessionCode, username);
    }
    
    
    
}
