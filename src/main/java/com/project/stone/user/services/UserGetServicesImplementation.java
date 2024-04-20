package com.project.stone.user.services;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
            e.printStackTrace();
            // Handle exceptions
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(user);
    }

    @Override
    public User getUserObjectByUsername(String username) throws UserException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, username, created_at FROM users WHERE username = ?")) {

            statement.setString(1, username);
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
            e.printStackTrace();
            // Handle exceptions
        }

        return user;
    }

    


    
}
