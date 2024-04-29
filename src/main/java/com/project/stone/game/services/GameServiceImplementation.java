package com.project.stone.game.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.project.stone.exceptions.CustomException;
import com.project.stone.game.dto.SingleRoundPayload;
import com.project.stone.game.dto.SuccessfulRoomCreationMessage;
import com.project.stone.game.entity.Room;

@Service
@Component
public class GameServiceImplementation implements GameService{

    private final RoomServiceImplementation roomServiceImplementation;
    private Gson gson = new Gson();
    private DataSource dataSource;

    @Autowired
    public GameServiceImplementation(Gson gson,
                                    RoomServiceImplementation roomServiceImplementation,
                                    DataSource dataSource) {
        this.gson = gson;
        this.roomServiceImplementation = roomServiceImplementation;
        this.dataSource = dataSource;
    }

    

    @Override
    public String playRound(SingleRoundPayload singleRoundPayload) throws RuntimeException, Exception {

        try{
            if(singleRoundPayload.getRound() == null){
                throw new NullPointerException();
            }
            singleRoundPayload.getSessionCode().isBlank();
            if(singleRoundPayload.getUserId() == null){
                throw new NullPointerException();
            }
            singleRoundPayload.getMove().isBlank();
        } catch(NullPointerException e){
            throw new CustomException("400", "sessionCode/username/move cannot be null");
        }

        Integer round = singleRoundPayload.getRound();
        String sessionCode = singleRoundPayload.getSessionCode();
        Integer userId = singleRoundPayload.getUserId();
        String move = singleRoundPayload.getMove();

        Room room = new Room();
        room = roomServiceImplementation.getRoomBySessionCode(sessionCode);

        if(room.getId() == 0){
            throw new CustomException("400", "Room not found. Pass proper roomId");
        }
        Integer sessionId = room.getId();

        if(room.getIsActive() == false){
            throw new CustomException("400", "Room is not active");
        }

        if(room.getIsVacant() == true){
            throw new CustomException("400", "Room is vacant. Please wait for another player to join");
        }

        if(room.getUser1Id() != userId && room.getUser2Id() != userId){
            throw new CustomException("400", "User not in the room. Invalid userId might have been used");
        }

        if(room.getWinnerId() != 0){
            throw new CustomException("400", "Game already ended. Winner is already declared");
        }

        if(round > 3){
            throw new CustomException("400", "Game already ended. Winner is already declared");
        }

        try(Connection connection = dataSource.getConnection();){

            PreparedStatement prePreStatement = connection.prepareStatement("SELECT * FROM game WHERE round_id = ? AND session_id = ?");

            prePreStatement.setInt(1, round);
            prePreStatement.setInt(2, sessionId);

            Integer count = 0;
            try(ResultSet resultSet = prePreStatement.executeQuery();){
                while(resultSet.next()){
                    count++;
                }
            }

            if(count > 1){
                throw new CustomException("400", "Round already played. Move to the next round");
            }

            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM game WHERE round_id = ? AND user_id = ? AND session_id = ?");
            
            preStatement.setInt(1, round);
            preStatement.setInt(2, userId);
            preStatement.setInt(3, sessionId);

            if(preStatement.executeQuery().next()){
                throw new CustomException("400", "Round already played. Please wait for the other player to play");
            }

            PreparedStatement statement = connection.prepareStatement("INSERT INTO game (round_id, user_id, user_choice, session_id) VALUES (?, ?, ?, ?)");

            statement.setInt(1, round);
            statement.setInt(2, userId);
            statement.setString(3, move);
            statement.setInt(4, sessionId);

            Integer rowsUpdated = statement.executeUpdate();

            if(rowsUpdated > 0){
                return gson.toJson(new SuccessfulRoomCreationMessage(room.getId(), "Round played successfully", room.getSessionCode()));
            } else {
                throw new CustomException("500", "Failed to update the room!");
            }

        } catch (RuntimeException e){
            throw new CustomException("400", e.getMessage());
        } catch (SQLException e){
            throw new CustomException("400", e.getMessage());
        }

    }
    
}
