package com.project.stone.game.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.stone.game.dto.SingleRoundPayload;

@Service
@Component
public interface GameService {

    String playRound(SingleRoundPayload singleRoundPayload) throws RuntimeException, Exception;
    
}
