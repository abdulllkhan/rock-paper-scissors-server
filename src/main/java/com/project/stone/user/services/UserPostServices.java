package com.project.stone.user.services;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.project.stone.user.dto.CreateUserDTO;

@Service
public interface UserPostServices {

    public String createUser(CreateUserDTO createUserDTO) throws RuntimeException, NoSuchAlgorithmException,Exception;

    public String userLogin(CreateUserDTO loginUserDTO) throws RuntimeException, NoSuchAlgorithmException, Exception;
    
}
