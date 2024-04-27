package com.project.stone.user.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.gson.Gson;
import com.project.stone.exceptions.CustomException;
import com.project.stone.sha.SHA256;
import com.project.stone.user.dto.CommonConstants;
import com.project.stone.user.dto.CreateUserDTO;
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
    public String createUser(@Valid CreateUserDTO createUserDTO) throws RuntimeException, NoSuchAlgorithmException, Exception {

        try{
            Optional.ofNullable(createUserDTO).orElseThrow(() -> new Exception("User cannot be null"));
        }catch(Exception e){
            throw new Exception("User cannot be null");
        }
        try{
            if(createUserDTO.getUsername().isBlank() || createUserDTO.getHashedPassword().isBlank()){
                throw new Exception("Username or password cannot be blank");
            }
        } catch (Exception e){
            return gson.toJson(e.getMessage());
            // throw new UserException(e.getMessage()); // work on overriding the validation to return proper message with error code instead of just throwing internal server error
        }

        User user = new User(); 
        user = userGetServices.getUserObjectByUsernameForInternal(createUserDTO.getUsername());

        if(user != null){
            throw new CustomException("400", "User already exists, please use a different username");
        }

        try (Connection connection = dataSource.getConnection();
            //  PreparedStatement statement = connection.prepareStatement("SELECT id, username,created_at FROM users WHERE id = ?")) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, digest, salt, created_at) VALUES (?, ?, ?, ?)")) {

            String createdSalt = generateRandomString(32);
            String createdDigest = SHA256.getSHA256(createUserDTO.getHashedPassword() + createdSalt + CommonConstants.PEPPER);
            Long currentTime = System.currentTimeMillis();
            
            preparedStatement.setString(1, createUserDTO.getUsername());
            preparedStatement.setString(2, createdDigest);
            preparedStatement.setString(3, createdSalt);
            preparedStatement.setLong(4, currentTime);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            } else {
                throw new CustomException("500", "Failed to insert a new user!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        user = userGetServices.getUserObjectByUsernameForInternal(createUserDTO.getUsername());

        return gson.toJson(new SuccessfulUserCreationMessage(user.getId(), "User created successfully"));

    }


    @Override
    public String userLogin(CreateUserDTO loginUserDTO) throws RuntimeException, NoSuchAlgorithmException, Exception{

        try{
            Optional.ofNullable(loginUserDTO).orElseThrow(() -> new Exception("User cannot be null"));
        }catch(Exception e){
            // throw new Exception("User cannot be null");
            return gson.toJson(e.getMessage());
        }
        try{
            if(loginUserDTO.getUsername().isBlank() || loginUserDTO.getHashedPassword().isBlank()){
                throw new RuntimeException("Username or password cannot be blank");
            }
        } catch (NullPointerException e){
            // throw new CustomException(HttpStatus.BAD_REQUEST , "Something is fishy");
            throw new CustomException(HttpStatus.BAD_REQUEST.toString(), "Username/password cannot be blank");
        } catch (Exception e){
            throw new CustomException("404" , e.getMessage());
        }

        User user = new User();
        user = userGetServices.getUserObjectByUsernameForInternal(loginUserDTO.getUsername());
        
        if(user == null){
            throw new CustomException("400", "User not found, use proper username");
        }

        if(verifyPassword(user, loginUserDTO.getHashedPassword())){
            return gson.toJson(new SuccessfulUserCreationMessage(user.getId(), "User logged in successfully"));
        } else {
            throw new CustomException("400", "Incorrect password, please try again");
        }

    }

    private Boolean verifyPassword(User user, String hashedPassword) throws RuntimeException, NoSuchAlgorithmException{

        String salt = user.getSalt();
        String digest = user.getDigest();
        
        String hashedPasswordToVerify = SHA256.getSHA256(hashedPassword + salt + CommonConstants.PEPPER);

        if(hashedPasswordToVerify.equals(digest)){
            return true;
        } else {
            return false;
        }

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
    
    
}
