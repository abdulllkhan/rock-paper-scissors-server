package com.project.stone.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.stone.user.entity.User;
import com.project.stone.user.entity.UserRepository;
import com.project.stone.user.exception.UserException;
import com.google.gson.Gson;

@Service
@Component
public class UserGetServicesImplementation implements UserGetServices{

    private final UserRepository userRepository;
    private Gson gson = new Gson();


    @Autowired
    public UserGetServicesImplementation(UserRepository userRepository,
                                        Gson gson) {
        this.userRepository = userRepository;
        this.gson = gson;
    }

    @Override
    public String getUserDetails(String userId) throws Exception{

        User user = new User();
        user = userRepository.findById(Integer.parseInt(userId));
        if(user == null){
            throw new UserException("User not found");
        }
        return gson.toJson(user);

    }

    


    
}
