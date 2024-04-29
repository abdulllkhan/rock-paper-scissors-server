package com.project.stone.game.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.project.stone.game.dto.CreateNewGameDTO;
import com.project.stone.game.dto.EndGameDTO;
import com.project.stone.game.dto.JoinGameDTO;
import com.project.stone.game.dto.SingleRoundPayload;
import com.project.stone.game.services.GameService;
import com.project.stone.game.services.RoomService;

@RestController
@Validated
public class GameController {

    private final RoomService roomService;
    private Gson gson = new Gson();
    private final GameService gameService;

    @Autowired
    public GameController(RoomService roomService,
                          Gson gson,
                          GameService gameService) {
        this.roomService = roomService;
        this.gson = gson;
        this.gameService = gameService;
    }

    @GetMapping("api/game/{sessionCode}")
    public String gameDetails(@PathVariable String sessionCode) throws Throwable{
        return roomService.getGameSessionDetails(sessionCode);
    }

    @PostMapping("api/game")
    public String createNewGame(@Valid @RequestBody CreateNewGameDTO createNewGameDTO) throws Throwable{
        return roomService.createNewGame(createNewGameDTO);
    }

    @PostMapping("api/game/join")
    public String joinGame(@Valid @RequestBody JoinGameDTO joinGameDTO) throws Throwable{
        return roomService.joinGame(joinGameDTO);
    }

    @PostMapping("api/game/end")
    public String endGame(@Valid @RequestBody EndGameDTO endGameDTO) throws Throwable{
        return roomService.endGame(endGameDTO);
    }

    @PostMapping("api/game/play")
    public String playRound(@Valid @RequestBody SingleRoundPayload singleRoundPayload) throws Throwable{
        return gameService.playRound(singleRoundPayload);
    }
     
}
