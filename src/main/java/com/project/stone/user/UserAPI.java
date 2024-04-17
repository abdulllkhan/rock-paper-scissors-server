package com.project.stone.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.project.stone.user.services.UserGetServices;

@RestController
// @RequestMapping("/api/user")
public class UserAPI {

    private final UserGetServices userGetServices;
    private Gson gson = new Gson();

    @Autowired
    public UserAPI(UserGetServices userGetServices,
                    Gson gson) {
        this.userGetServices = userGetServices;
        this.gson = gson;
    }

    @GetMapping("api/user")
    public String sayHello() {
        return "Hello, User!";
    }

    @GetMapping("api/user/{id}")
    public String getUserDetails(@PathVariable String id) throws Exception {
        try{
            return userGetServices.getUserDetails(id);
        } catch (Exception e) {
            return gson.toJson(e.getMessage());
        }
    }
    
}
