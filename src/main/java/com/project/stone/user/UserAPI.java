package com.project.stone.user;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.stone.user.entity.CreateUserDTO;
import com.project.stone.user.services.UserGetServices;
import com.project.stone.user.services.UserPostServices;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
// @RequestMapping("/api/user")
@Validated
public class UserAPI {

    private final UserGetServices userGetServices;
    private final UserPostServices userPostServices;    
    private Gson gson = new Gson();
    private Gson gson2 = new GsonBuilder().create();

    @Autowired
    public UserAPI(UserGetServices userGetServices,
                    Gson gson,
                    UserPostServices userPostServices) {
        this.userGetServices = userGetServices;
        this.gson = gson;
        this.userPostServices = userPostServices;
    }

    @GetMapping("api/user")
    public String sayHello() {
        return "Hello, User!";
    }

    @GetMapping("api/user/{id}")
    public String getUserDetails(@PathVariable Integer id) throws Exception {
        try{
            return userGetServices.getUserByIdAsJson(id);
        } catch (Exception e) {
            return gson2.toJson("{ message: " + e.getMessage() + "}"); // dumb paranthesis, check later how to actually return a json object
        }
        // return id;
    }

    @PostMapping("api/user")
    public String createUser(@Valid @RequestBody CreateUserDTO createUserDTO) throws Exception {

        return userPostServices.createUser(createUserDTO);

    }

    @PostMapping("api/login")
    public String userLogin(@Valid @RequestBody CreateUserDTO loginUserDTO) throws Exception {

        return userPostServices.userLogin(loginUserDTO);

    }


    
}
