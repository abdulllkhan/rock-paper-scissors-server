package com.project.stone.game.services;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.project.stone.exceptions.CustomException;
import com.project.stone.game.dto.CreateNewGameDTO;
import com.project.stone.game.dto.EndGameDTO;
import com.project.stone.game.dto.JoinGameDTO;
import com.project.stone.game.dto.JoiningGameSuccessfullMessage;
import com.project.stone.game.dto.SuccessfulRoomCreationMessage;
import com.project.stone.game.entity.GameRepository;
import com.project.stone.game.entity.Room;
import com.project.stone.game.entity.RoomRepository;
import com.project.stone.user.dto.CommonConstants;
import com.project.stone.user.entity.User;
import com.project.stone.user.entity.UserRepository;

import javax.sql.DataSource;

@Service
@Component
public class RoomServiceImplementation implements RoomService{

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private DataSource dataSource;
    private Gson gson = new Gson();

    @Autowired
    public RoomServiceImplementation(RoomRepository roomRepository,
                                     UserRepository userRepository,
                                     DataSource dataSource,
                                     Gson gson,
                                     GameRepository gameRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.dataSource = dataSource;
        this.gson = gson;
        this.gameRepository = gameRepository;
    }

    @Override
    public Room getRoomDetailsById(Integer roomId) {
        Room room = new Room();
        room = roomRepository.findById(roomId).orElse(null);
        return room;
    }

    @Override
    public Room getRoomDetailsBySessionCode(String sessionCode) {
        Optional<Room> room = Optional.ofNullable(new Room());
        room = Optional.ofNullable(roomRepository.findBySessionCode(sessionCode));
        return room.get();
    }

	@Override
	public String getGameSessionDetails(String sessionCode) {

        Room room;
        room = getRoomBySessionCode(sessionCode);
        return gson.toJson(room);

	}


    @Override
    public String endGame(EndGameDTO endGameDTO) throws RuntimeException, Exception {

        try{
            endGameDTO.getSessionCode();
        } catch(NullPointerException e){
            throw new CustomException("400", "Room ID cannot be null");
        }

        String sessionCode = endGameDTO.getSessionCode();

        Room room = new Room();
        room = getRoomBySessionCode(sessionCode);

        if(room.getId() == 0){
            throw new CustomException("400", "Room not found. Pass proper roomId");
        }

        if(room.getIsActive() == false){
            throw new CustomException("400", "Room is not active");
        }

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE rooms SET is_active = false WHERE session_code = ?")){

            statement.setString(1, sessionCode);

            int rowsUpdated = statement.executeUpdate();

            if(rowsUpdated > 0){
                return gson.toJson(new SuccessfulRoomCreationMessage(room.getId(), "Game ended successfully. Room closed.", room.getSessionCode()));
            } else {
                throw new CustomException("500", "Failed to end the room!");
            }

        } catch (RuntimeException e){
            throw new CustomException("500", e.getMessage());
        } catch (SQLException e){
            throw new CustomException("400", e.getMessage());
        }

    }

    @Override
    public String joinGame(JoinGameDTO joinGameDTO) throws RuntimeException, Exception {

        try{
            joinGameDTO.getUserId();
            joinGameDTO.getSessionCode();
        } catch(NullPointerException e){
            throw new CustomException("400", "User ID/Session Code cannot be null");
        }

        Integer userId = joinGameDTO.getUserId();
        String sessionCode = joinGameDTO.getSessionCode();

        User user = new User();
        user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new CustomException("400", "User not found. Pass proper userId");
        }

        Room room = new Room();
        room = getRoomBySessionCode(sessionCode);

        if(room.getId() == 0){
            throw new CustomException("400", "Room not found. Pass proper sessionCode");
        }

        if(room.getIsVacant() == false){
            throw new CustomException("400", "Room is not vacant");
        }

        if(room.getIsActive() == false){
            throw new CustomException("400", "Room is not active");
        }

        if(room.getUser1Id() == userId){
            throw new CustomException("400", "User is already in the room");
        }

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE rooms SET user2_id = ?, is_vacant = false WHERE session_code = ?")){

            statement.setInt(1, userId);
            statement.setString(2, sessionCode);

            int rowsUpdated = statement.executeUpdate();

            if(rowsUpdated > 0){
                return gson.toJson(new JoiningGameSuccessfullMessage(room.getId(), "Room joined successfully", sessionCode, room.getUser1Id()));
            } else {
                throw new CustomException("500", "Failed to update the room!");
            }

        } catch (RuntimeException e){
            throw new CustomException("500", e.getMessage());
        } catch (SQLException e){
            throw new CustomException("400", e.getMessage());
        }

    }

    @Override
    public String createNewGame(CreateNewGameDTO createNewGameDTO) throws RuntimeException, Exception{

        try{
            createNewGameDTO.getUserId();
        } catch(NullPointerException e){
            throw new CustomException("400", "User ID cannot be null");
        }
        Integer userId = createNewGameDTO.getUserId();

        User user = new User();
        user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new CustomException("400", "User not found. Pass proper userId");
        }

        // checking if the user already has an active game session
        if(activeUserSessionsOfUser(userId)){
            throw new CustomException("400", "User already has an active game session");
        }

        // creating a new room with the current user
        Room room = new Room();

        try(Connection connection = dataSource.getConnection();
            
            PreparedStatement statement = connection.prepareStatement("INSERT INTO rooms (session_code, user1_id, is_vacant, is_active) VALUES (?, ?, true, true)");){

            String sessionCode = generateRandomString(10);

            statement.setString(1, sessionCode);
            statement.setInt(2, userId);

            int rowsInserted = statement.executeUpdate();

            // room = roomRepository.findBySessionCode(room.getSessionCode()); 
            room = getRoomBySessionCode(sessionCode);

            if(rowsInserted > 0){
                return gson.toJson(new SuccessfulRoomCreationMessage(room.getId(), "Room created successfully", sessionCode));
            } else {
                throw new CustomException("500", "Failed to insert a new room!");
            }

        } catch (RuntimeException e){
            throw new CustomException("500", e.getMessage());
        } catch (SQLException e){
            throw new CustomException("400", e.getMessage());
        }


    }

    public Boolean activeUserSessionsOfUser(Integer userId){

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms WHERE ( user1_id = ? OR user2_id = ? ) AND is_active = true")){

            statement.setInt(1, userId);
            statement.setInt(2, userId);
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    return true;
                }
            }

        }catch(SQLException e){
            throw new CustomException("400", e.getMessage());
        }catch(RuntimeException e){
            throw new CustomException("500", e.getMessage());
        }

        return false;
    }

    public static String generateRandomString(int length) {

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CommonConstants.ALPHABETS.length());
            sb.append(CommonConstants.ALPHABETS.charAt(randomIndex));
        }
        return sb.toString();

    }

    public Boolean isSessionCodeValid(String sessionCode){
            
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms WHERE session_code = ?")){
            
            statement.setString(1, sessionCode);
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    return true;
                }
            }

        }catch(SQLException e){
            throw new CustomException("400", e.getMessage());
        }catch(RuntimeException e){
            throw new CustomException("500", e.getMessage());
        }
    
        return false;
    }

    public Boolean isSessionActive(String sessionCode){
            
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms WHERE session_code = ? AND is_active = true")){
            
            statement.setString(1, sessionCode);
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    return true;
                }
            }

        }catch(SQLException e){
            throw new CustomException("400", e.getMessage());
        }catch(RuntimeException e){
            throw new CustomException("500", e.getMessage());
        }
    
        return false;
    }
    
    public Room getRoomBySessionCode(String sessionCode){

        Room room = new Room();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms WHERE session_code = ? ")){

            statement.setString(1, sessionCode);
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    room.setId(resultSet.getInt("id"));
                    room.setSessionCode(resultSet.getString("session_code"));
                    room.setUser1Id(resultSet.getInt("user1_id"));
                    room.setUser2Id(resultSet.getInt("user2_id"));
                    room.setIsVacant(resultSet.getBoolean("is_vacant"));
                    room.setIsActive(resultSet.getBoolean("is_active"));
                    // room.setWinnerId(resultSet.getString("winner_id"));
                    room.setWinnerId(resultSet.getInt("winner_id"));
                }
            }

        }catch(SQLException e){
            throw new CustomException("400", e.getMessage());
        }catch(RuntimeException e){
            throw new CustomException("500", e.getMessage());
        }

        return room;

    }

    public Room getRoomById(Integer roomId){

        Room room = new Room();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms WHERE id = ? ")){

            statement.setInt(1, roomId);
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    room.setId(resultSet.getInt("id"));
                    room.setSessionCode(resultSet.getString("session_code"));
                    room.setUser1Id(resultSet.getInt("user1_id"));
                    room.setUser2Id(resultSet.getInt("user2_id"));
                    room.setIsVacant(resultSet.getBoolean("is_vacant"));
                    room.setIsActive(resultSet.getBoolean("is_active"));
                    // room.setWinnerId(resultSet.getString("winner_id"));
                    room.setWinnerId(resultSet.getInt("winner_id"));
                }
            }

        }catch(SQLException e){
            throw new CustomException("400", e.getMessage());
        }catch(RuntimeException e){
            throw new CustomException("500", e.getMessage());
        }

        return room;

    }
    
    
}
