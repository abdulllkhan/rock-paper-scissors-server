package com.project.stone.game.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.stone.game.entity.CreateNewGameDTO;
import com.project.stone.game.entity.Room;

@Service
@Component
public interface RoomService {

    Room getRoomDetailsById(Integer roomId);

    Room getRoomDetailsBySessionCode(String sessionCode);

    String getGameSessionDetails(String sessionCode) throws RuntimeException, Exception;

    String createNewGame(CreateNewGameDTO createNewGameDTO) throws RuntimeException, Exception;

}
