package com.project.stone.messages.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.stone.exceptions.CustomException;
import com.project.stone.game.entity.Room;
import com.project.stone.game.services.RoomService;
import com.project.stone.game.services.RoomServiceImplementation;
import com.project.stone.messages.dto.FetchMessagesDTO;
import com.project.stone.messages.dto.FetchMessagesDTO.MessageDetails;
import com.project.stone.messages.dto.ResponseMessage;

import com.google.gson.Gson;
import com.project.stone.messages.dto.SendMesssagePayload;
import com.project.stone.messages.entity.MessageRepository;
import com.project.stone.user.entity.User;
import com.project.stone.user.services.UserGetServicesImplementation;


@Service
@Component
public class MessageServiceImplementation implements MessageService{

    private final MessageRepository messageRepository;
    private final RoomService roomService;
    private final RoomServiceImplementation roomServiceImplementation;
    private final UserGetServicesImplementation userGetServicesImplementation;
    private DataSource dataSource;
    private Gson gson = new Gson();

    @Autowired
    MessageServiceImplementation(MessageRepository messageRepository,
                                 Gson gson,
                                 RoomService roomService,
                                 RoomServiceImplementation roomServiceImplementation,
                                 UserGetServicesImplementation userGetServicesImplementation,
                                 DataSource dataSource){
        this.messageRepository = messageRepository;
        this.roomService = roomService;
        this.gson = gson;
        this.roomServiceImplementation = roomServiceImplementation;
        this.userGetServicesImplementation = userGetServicesImplementation;
        this.dataSource = dataSource;
    }

    @Override
    public String fetchNewMessages(String sessionCode, String username) {

        // verify if sessionCode and username are not null
        try {
            username.isBlank();
            sessionCode.isBlank();
        } catch (NullPointerException e) {
            throw new CustomException("400", "username and sessionCode are mandatory fields");
        }

        try{
            // verifying if the session code is valid
            if(!roomServiceImplementation.isSessionCodeValid(sessionCode)){
                throw new CustomException("400", "Wrong session code passed in the payload. Pass proper sessionCode");
            }
            if(!roomServiceImplementation.isSessionActive(sessionCode)){
                throw new CustomException("400", "Session is no longer active");
            }
    
            Room room = roomServiceImplementation.getRoomBySessionCode(sessionCode);
            User user = userGetServicesImplementation.getUserObjectByUsernameForInternal(username);
    
            if(!(room.getUser1Id() == user.getId() || room.getUser2Id() == user.getId())){
                throw new CustomException("400", "User is not a part of this room. Wrong username");
            }

            FetchMessagesDTO fetchMessagesDTO = new FetchMessagesDTO();
            ArrayList<MessageDetails> messages = new ArrayList<>();

            // default values
            fetchMessagesDTO.setHasMessages(false);
            fetchMessagesDTO.setMessageCount(0);
            fetchMessagesDTO.setMessages(messages);

            try{
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE (session_code = ? AND username != ? AND is_fetched = false) ORDER BY created_at DESC");

                statement.setString(1, sessionCode);
                statement.setString(2, username);

                ResultSet resultSet = statement.executeQuery();

                Integer messageCount = 0;

                while(resultSet.next()){
                    messageCount++;
                    MessageDetails messageDetails = fetchMessagesDTO.new MessageDetails();

                    messageDetails.setMessage(resultSet.getString("message"));
                    messageDetails.setSender(resultSet.getString("username"));
                    messageDetails.setCreatedAt(resultSet.getLong("created_at"));

                    messages.add(messageDetails);

                }

                if(messageCount > 0){
                    fetchMessagesDTO.setHasMessages(true);
                    fetchMessagesDTO.setMessageCount(messageCount);
                    fetchMessagesDTO.setMessages(messages);
                }

                // updating the is_fetched column to true
                statement = connection.prepareStatement("UPDATE messages SET is_fetched = true WHERE (session_code = ? AND username != ? AND is_fetched = false)");

                statement.setString(1, sessionCode);
                statement.setString(2, username);

                Integer rowsChanged = statement.executeUpdate();
                if(rowsChanged != messageCount){
                    System.out.println("Failed to update all rows. There might be an issue in the database.");
                    // throw new CustomException("500", "Failed to update the is_fetched column");
                }

                return gson.toJson(fetchMessagesDTO);

            } catch(SQLException e){
                throw new CustomException("400", "SQL Error. " + e.getMessage());
            }


        } catch (RuntimeException e){
            throw new CustomException("400", e.getMessage());
        }

    }

    @Override
    public String sendMessage(SendMesssagePayload sendMesssagePayload) {

        // verify if sessionCode, sender and message are not null
        try {
            sendMesssagePayload.getSender().isBlank();
            sendMesssagePayload.getMessage().isBlank();
            sendMesssagePayload.getSessionCode().isBlank();
        } catch (NullPointerException e) {
            throw new CustomException("400", "sender(username), message and sessionCode are mandatory fields");
        }
        try{
            String sessionCode = sendMesssagePayload.getSessionCode();
            // verifying if the session code is valid
            if(!roomServiceImplementation.isSessionCodeValid(sessionCode)){
                throw new CustomException("400", "Wrong session code passed in the payload. Pass proper sessionCode");
            }
            if(!roomServiceImplementation.isSessionActive(sessionCode)){
                throw new CustomException("400", "Session is no longer active");
            }
    
            Room room = roomServiceImplementation.getRoomBySessionCode(sendMesssagePayload.getSessionCode());
            User user = userGetServicesImplementation.getUserObjectByUsernameForInternal(sendMesssagePayload.getSender());
    
            if(!(room.getUser1Id() == user.getId() || room.getUser2Id() == user.getId())){
                throw new CustomException("400", "Sender is not a part of this room");
            }

            try{
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO messages (username, message, session_code, created_at) VALUES (?, ?, ?, ?)");

                statement.setString(1, sendMesssagePayload.getSender());
                statement.setString(2, sendMesssagePayload.getMessage());
                statement.setString(3, sessionCode);
                statement.setLong(4, System.currentTimeMillis());

                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("A new message was inserted successfully!");
                    return gson.toJson(new ResponseMessage("Message sent successfully"));
                } else {
                    throw new CustomException("500", "Failed to send the message!");
                }

            } catch(SQLException e){
                throw new CustomException("400", "SQL Error. " + e.getMessage());
            }

        } catch (RuntimeException e){
            throw new CustomException("400", e.getMessage());
        }

        // return gson.toJson(new ResponseMessage("Message sent successfully"));

    }
    
}
