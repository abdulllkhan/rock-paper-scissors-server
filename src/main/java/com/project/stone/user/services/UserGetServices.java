package com.project.stone.user.services;

import org.springframework.stereotype.Service;

import com.project.stone.user.exception.UserException;

@Service
public interface UserGetServices {
    
    public String getUserDetails(String userId) throws Exception;

}
