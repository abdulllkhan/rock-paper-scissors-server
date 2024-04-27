package com.project.stone.user.services;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.stone.exceptions.CustomException;
import com.project.stone.user.dto.HighScoreDTO;
import com.project.stone.user.dto.HighScoreDTO.ScoreInfo;
import com.project.stone.user.entity.User;
import com.project.stone.user.entity.UserRepository;
import com.project.stone.user.exception.UserException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
@Component
public class UserGetServicesImplementation implements UserGetServices{

    private final UserRepository userRepository;
    private Gson gson = new Gson();
    private DataSource dataSource;

    @Autowired
    public UserGetServicesImplementation(UserRepository userRepository,
                                        Gson gson,
                                        DataSource dataSource) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.dataSource = dataSource;
    }

    @Override
    public String getUserDetails(Integer userId) throws UserException{

        User user = new User();
        // user = userRepository.findById(Integer.parseInt(userId));
        user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserException("User not found");
        }
        return gson.toJson(user);

    }

    @Override
    public String getUserByIdAsJson(Integer userId) throws UserException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, username,created_at FROM users WHERE id = ?")) {

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    // user.setEmail(resultSet.getString("email"));
                    user.setUsername(resultSet.getString("username"));
                    user.setCreatedAt(resultSet.getLong("created_at"));
                }
            }
        } catch (SQLException e) {
            throw new CustomException("SQL", e.getMessage());
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(user);
    }

    @Override
    public User getUserObjectByUsernameForInternal(String username) throws UserException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, username, digest, salt, score, created_at FROM users WHERE username = ?")) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    // user.setEmail(resultSet.getString("email"));
                    user.setDigest(resultSet.getString("digest"));
                    user.setSalt(resultSet.getString("salt"));
                    user.setUsername(resultSet.getString("username"));
                    user.setCreatedAt(resultSet.getLong("created_at"));
                    user.setScore(resultSet.getInt("score"));
                }
            }
        } catch (SQLException e) {
            throw new CustomException("SQL", e.getMessage());
        }

        return user;
    }

    @Override
    public HighScoreDTO getHighScores() throws RuntimeException {
        
        HighScoreDTO highScoreDTO = new HighScoreDTO();
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, username, score FROM users ORDER BY score DESC LIMIT 10")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setScore(resultSet.getInt("score"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new CustomException("SQL", e.getMessage());
        }

        highScoreDTO.setHighestScore(users.get(0).getScore());
        highScoreDTO.setTotalScores(users.size());
        highScoreDTO.setHighScores(users.stream().map(user -> highScoreDTO.new ScoreInfo(user.getScore(), user.getUsername())).toArray(ScoreInfo[]::new));

        return highScoreDTO;


    }

    


    
}
