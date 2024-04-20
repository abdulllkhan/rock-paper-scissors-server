package com.project.stone.user.services;

import org.springframework.stereotype.Service;

import com.project.stone.user.entity.CreateUserDTO;

@Service
public interface UserPostServices {

    public String createUser(CreateUserDTO createUserDTO) throws Exception;

    public String userLogin(CreateUserDTO loginUserDTO) throws Exception;
    
}
