package com.blockchain.ibizeebe.Controller;

import com.blockchain.ibizeebe.Model.Login;
import com.blockchain.ibizeebe.Model.UserResponse;
import com.blockchain.ibizeebe.Model.Users;
import com.blockchain.ibizeebe.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * function to register user in the database
     * @return createUser
     */
    @PostMapping(value = "/user/register")
    private UserResponse registerUser(@RequestBody Users user) {
        return userService.registerUser(user);
    }

    /**
     * function to handle user logins by checking the email and the password
     */
    @PostMapping(value = "/user/login")
    private UserResponse userLogin(@RequestBody Login login) {
        return userService.userLogin(login);
    }
}
