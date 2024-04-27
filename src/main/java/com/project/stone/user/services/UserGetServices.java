package com.project.stone.user.services;

import org.springframework.stereotype.Service;

import com.project.stone.exceptions.CustomException;
import com.project.stone.user.dto.HighScoreDTO;
import com.project.stone.user.entity.User;
import com.project.stone.user.exception.UserException;

@Service
public interface UserGetServices {
    
    public String getUserDetails(Integer userId) throws Exception;

    public String getUserByIdAsJson(Integer userId) throws CustomException;

    public User getUserObjectByUsernameForInternal(String username) throws UserException;

    public HighScoreDTO getHighScores() throws RuntimeException;

}
