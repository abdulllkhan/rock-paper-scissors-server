package com.project.stone.user.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.gson.Gson;
import com.project.stone.user.entity.CreateUserDTO;
import com.project.stone.user.entity.User;


@Service
@Component
@Validated
public class UserPostServicesImplementation implements UserPostServices{

    private Gson gson = new Gson();
    private DataSource dataSource;
    private UserGetServices userGetServices;    
    
    @Autowired
    public UserPostServicesImplementation(Gson gson,
                                          DataSource dataSource,
                                          UserGetServices userGetServices) {
        this.gson = gson;
        this.dataSource = dataSource;
        this.userGetServices = userGetServices;
    }


    @Override
    public String createUser(@Valid CreateUserDTO createUserDTO) throws Exception {

        Integer id = null;

        try{
            Optional.ofNullable(createUserDTO).orElseThrow(() -> new Exception("User cannot be null"));
        }catch(Exception e){
            throw new Exception("User cannot be null");
        }
        try{
            if(createUserDTO.getUsername().isBlank() || createUserDTO.getPassword().isBlank()){
                throw new Exception("Username or password cannot be blank");
            }
        } catch (Exception e){
            return gson.toJson(e.getMessage());
            // throw new UserException(e.getMessage()); // work on overriding the validation to return proper message with error code instead of just throwing internal server error
        }

        User user = new User(); 
        user = userGetServices.getUserObjectByUsername(createUserDTO.getUsername());

        if(user != null){
            return gson.toJson("User already exists, please use a diffrent username");
        }

        try (Connection connection = dataSource.getConnection();
            //  PreparedStatement statement = connection.prepareStatement("SELECT id, username,created_at FROM users WHERE id = ?")) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, created_at) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, createUserDTO.getUsername());
            preparedStatement.setString(2, createUserDTO.getPassword());
            preparedStatement.setLong(3, System.currentTimeMillis());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            } else {
                System.out.println("Failed to insert a new user!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        user = userGetServices.getUserObjectByUsername(createUserDTO.getUsername());

        return gson.toJson(new SuccessfulUserCreationMessage(user.getId(), "User created successfully"));
        // return createUserDTO.getUsername();

    }
    
    
}
