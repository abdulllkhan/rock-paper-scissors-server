package com.project.stone.user.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.stone.user.entity.CreateUserDTO;


@Service
@Component
public class UserPostServicesImplementation implements UserPostServices{

    @Override
    public String createUser(CreateUserDTO createUserDTO) throws Exception {




        return "null";

    }
    
    
}
