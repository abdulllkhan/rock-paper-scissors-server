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
import com.project.stone.game.dto.RoundWinnerDetails;
import com.project.stone.game.dto.SingleRoundPayload;
import com.project.stone.game.dto.SuccessfulRoomCreationMessage;
import com.project.stone.game.entity.RockPaperScissiors;
import com.project.stone.game.entity.Room;
import com.project.stone.user.entity.User;
import com.project.stone.user.services.UserGetServicesImplementation;
import com.project.stone.game.dto.GameWinnerDetails;

@Service
@Component
public class GameServiceImplementation implements GameService{

    private final RoomServiceImplementation roomServiceImplementation;
    private final UserGetServicesImplementation userGetServicesImplementation;
    private Gson gson = new Gson();
    private DataSource dataSource;

    @Autowired
    public GameServiceImplementation(Gson gson,
                                    RoomServiceImplementation roomServiceImplementation,
                                    DataSource dataSource,
                                    UserGetServicesImplementation userGetServicesImplementation) {
        this.gson = gson;
        this.roomServiceImplementation = roomServiceImplementation;
        this.dataSource = dataSource;
        this.userGetServicesImplementation = userGetServicesImplementation;
    }

    @Override
    public String fetchGameWinner(String sessionCode) throws RuntimeException, Exception {

        try{
            sessionCode.isBlank();
        } catch(NullPointerException e){
            throw new CustomException("400", "sessionCode cannot be null");
        }

        Room room = new Room();
        room = roomServiceImplementation.getRoomBySessionCode(sessionCode);

        if(room.getId() == 0){
            throw new CustomException("400", "Room not found. Pass proper roomId");
        }
        Integer sessionId = room.getId();

        // if(room.getIsActive() == false){
        //     throw new CustomException("400", "Room is not active");
        // }

        if(room.getIsVacant() == true){
            throw new CustomException("400", "Room is vacant. Please wait for another player to join");
        }

        try(Connection connection = dataSource.getConnection();){

            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM game WHERE session_id = ?");

            preStatement.setInt(1, sessionId);

            Integer count = 0;
            try(ResultSet resultSet = preStatement.executeQuery();){
                while(resultSet.next()){
                    count++;
                }
            }

            if(count < 6){
                throw new CustomException("400", "All rounds have not been played yet");
            }

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM game WHERE session_id = ?");

            statement.setInt(1, sessionId);

            String user1Move = "";
            String user2Move = "";
            String user3Move = "";
            String user4Move = "";
            String user5Move = "";
            String user6Move = "";

            try(ResultSet resultSet = statement.executeQuery();){
                while(resultSet.next()){
                    if(resultSet.getInt("round_id") == 1){
                        if(resultSet.getInt("user_id") == room.getUser1Id()){
                            user1Move = resultSet.getString("user_choice");
                        } else {
                            user2Move = resultSet.getString("user_choice");
                        }
                    } else if(resultSet.getInt("round_id") == 2){
                        if(resultSet.getInt("user_id") == room.getUser1Id()){
                            user3Move = resultSet.getString("user_choice");
                        } else {
                            user4Move = resultSet.getString("user_choice");
                        }
                    } else {
                        if(resultSet.getInt("user_id") == room.getUser1Id()){
                            user5Move = resultSet.getString("user_choice");
                        } else {
                            user6Move = resultSet.getString("user_choice");
                        }
                    }
                }
            }

            Integer user1Wins = 0;
            Integer user2Wins = 0;

            RockPaperScissiors user1MoveEnum = RockPaperScissiors.fromString(user1Move);
            RockPaperScissiors user2MoveEnum = RockPaperScissiors.fromString(user2Move);
            RockPaperScissiors user3MoveEnum = RockPaperScissiors.fromString(user3Move);
            RockPaperScissiors user4MoveEnum = RockPaperScissiors.fromString(user4Move);
            RockPaperScissiors user5MoveEnum = RockPaperScissiors.fromString(user5Move);
            RockPaperScissiors user6MoveEnum = RockPaperScissiors.fromString(user6Move);

            if(user1Move.equals(user2Move)){
                user1Wins++;
                user2Wins++;
            } else if(user1Move.equals("rock") && user2Move.equals("scissors")){
                user1Wins++;
            } else if(user1Move.equals("scissors") && user2Move.equals("paper")){
                user1Wins++;
            } else if(user1Move.equals("paper") && user2Move.equals("rock")){
                user1Wins++;
            } else {
                user2Wins++;
            }

            if(user3Move.equals(user4Move)){
                user1Wins++;
                user2Wins++;
            } else if(user3Move.equals("rock") && user4Move.equals("scissors")){
                user1Wins++;
            } else if(user3Move.equals("scissors") && user4Move.equals("paper")){
                user1Wins++;
            } else if(user3Move.equals("paper") && user4Move.equals("rock")){
                user1Wins++;
            } else {
                user2Wins++;
            }

            if(user5Move.equals(user6Move)){
                user1Wins++;
                user2Wins++;
            } else if(user5Move.equals("rock") && user6Move.equals("scissors")){
                user1Wins++;
            } else if(user5Move.equals("scissors") && user6Move.equals("paper")){
                user1Wins++;
            } else if(user5Move.equals("paper") && user6Move.equals("rock")){
                user1Wins++;
            } else {
                user2Wins++;
            }

            
            GameWinnerDetails gamerWinnerDetails = new GameWinnerDetails();
            gamerWinnerDetails.setSessionCode(sessionCode);
            gamerWinnerDetails.setUserId1(room.getUser1Id());
            gamerWinnerDetails.setUserId2(room.getUser2Id());
            gamerWinnerDetails.setMessage("Game Drawn");
            
            Integer winnerId = 0;
            
            if(user1Wins > user2Wins){
                winnerId = room.getUser1Id();
            } else if(user1Wins < user2Wins){
                winnerId = room.getUser2Id();
            }
            gamerWinnerDetails.setWinnerId(winnerId);

            if(winnerId != 0){
                room.setWinnerId(winnerId);
    
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE rooms SET winner_id = ? WHERE id = ?");
    
                updateStatement.setInt(1, winnerId);
                updateStatement.setInt(2, sessionId);
                
                updateStatement.executeUpdate();

                User user = new User();
                user = userGetServicesImplementation.getUserObjectByIdForInternal(winnerId);
                user.setScore(user.getScore() + 1);
                gamerWinnerDetails.setMessage(user.getUsername() + " won");
                gamerWinnerDetails.setWinnerName(user.getUsername());
            }

            return gson.toJson(gamerWinnerDetails);
        
        } catch (RuntimeException e){
            throw new CustomException("400", e.getMessage());
        } catch (SQLException e){
            throw new CustomException("400", e.getMessage());
        }

    }
    
    @Override
    public String fetchRoundWinner(String sessionCode, String round) throws RuntimeException, Exception {

        try{
            sessionCode.isBlank();
            round.isBlank();
        } catch(NullPointerException e){
            throw new CustomException("400", "sessionCode/round cannot be null");
        }

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

        // if(room.getWinnerId() != 0){
        //     throw new CustomException("400", "Game already ended. Winner is already declared");
        // }

        try(Connection connection = dataSource.getConnection();){

            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM game WHERE round_id = ? AND session_id = ?");

            preStatement.setInt(1, Integer.parseInt(round));
            preStatement.setInt(2, sessionId);

            Integer count = 0;
            try(ResultSet resultSet = preStatement.executeQuery();){
                while(resultSet.next()){
                    count++;
                }
            }

            if(count < 2){
                throw new CustomException("400", "Both players have not played the round yet");
            }

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM game WHERE round_id = ? AND session_id = ?");

            statement.setInt(1, Integer.parseInt(round));
            statement.setInt(2, sessionId);

            String user1Move = "";
            String user2Move = "";

            try(ResultSet resultSet = statement.executeQuery();){
                while(resultSet.next()){
                    if(resultSet.getInt("user_id") == room.getUser1Id()){
                        user1Move = resultSet.getString("user_choice");
                    } else {
                        user2Move = resultSet.getString("user_choice");
                    }
                }
            }

            RoundWinnerDetails roundWinnerDetails = new RoundWinnerDetails();
            roundWinnerDetails.setSessionCode(sessionCode);
            roundWinnerDetails.setRound(Integer.parseInt(round));
            roundWinnerDetails.setUserId1(room.getUser1Id());
            roundWinnerDetails.setUserId2(room.getUser2Id());
            roundWinnerDetails.setUserId1Choice(user1Move);
            roundWinnerDetails.setUserId2Choice(user2Move);

            RockPaperScissiors user1MoveEnum = RockPaperScissiors.fromString(user1Move);
            RockPaperScissiors user2MoveEnum = RockPaperScissiors.fromString(user2Move);
            Integer winnerId = 0;

            if(user1Move.equals(user2Move)){
                winnerId = 0;
            } else if(user1Move.equals("rock") && user2Move.equals("scissors")){
                winnerId = room.getUser1Id();
            } else if(user1Move.equals("scissors") && user2Move.equals("paper")){
                winnerId = room.getUser1Id();
            } else if(user1Move.equals("paper") && user2Move.equals("rock")){
                winnerId = room.getUser1Id();
            } else {
                winnerId = room.getUser2Id();
            }
            
            if(winnerId != 0){
                roundWinnerDetails.setWinnerId(winnerId);
    
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE game SET winner_id = ? WHERE session_id = ? and round_id = ?");
    
                updateStatement.setInt(1, winnerId);
                updateStatement.setInt(2, sessionId);
                updateStatement.setInt(3, Integer.parseInt(round));
                
                updateStatement.executeUpdate();
            }

            // if(rowsUpdated > 0){
                // return gson.toJson(roundWinnerDetails);
            // } else {
            //     throw new CustomException("500", "Failed to update the room!");
            // }

            return gson.toJson(roundWinnerDetails);

        } catch (RuntimeException e){
            throw new CustomException("400", e.getMessage());
        } catch (SQLException e){
            throw new CustomException("400", e.getMessage());
        }

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
        if(RockPaperScissiors.fromString(singleRoundPayload.getMove()) == null){
            throw new CustomException("400", "Invalid move. Please pass rock/paper/scissors");
        }
        String move = RockPaperScissiors.toString(RockPaperScissiors.fromString(singleRoundPayload.getMove()));

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
