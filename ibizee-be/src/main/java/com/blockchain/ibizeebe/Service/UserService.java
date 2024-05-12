package com.blockchain.ibizeebe.Service;

import com.blockchain.ibizeebe.Model.Login;
import com.blockchain.ibizeebe.Model.UserResponse;
import com.blockchain.ibizeebe.Model.Users;
import com.blockchain.ibizeebe.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    UserResponse userResponse = new UserResponse();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * register a new user into the system
     */
    public UserResponse registerUser(Users user) {
        System.out.println("***** registerUser function is starting *****");
        Users dbUser = new Users();
        if (user != null) {
            try {
                // checking whether the user existing in the database
                dbUser = userRepository.findByUserEmail(user.getUserEmail());

                // if the user found in the database
                if (dbUser != null) {
                    // user email is taken
                    System.out.println("User already exists");
                    userResponse.setResponseStatus(400);
                    userResponse.setResponseDescription("User already exists!");
                    userResponse.setUsers(null);
                }
                else {
                    // get current timestamp
                    user.setUserCreatedDate(new Timestamp(System.currentTimeMillis()));

                    // adding user to the database
                    dbUser = userRepository.save(user);

                    if (dbUser != null) {
                        System.out.println("user registered successfully");
                        userResponse.setResponseStatus(200);
                        userResponse.setResponseDescription("User registered successfully");
                        userResponse.setUsers(dbUser);
                    }
                }

            } catch (Exception ex) {
                System.out.println("user registration failed!");
                userResponse.setResponseStatus(400);
                userResponse.setResponseDescription("User registration failed!");
                userResponse.setUsers(null);
                ex.printStackTrace();
            }
        }
        return userResponse;
    }

    /**
     * function to handle user login
     */
    public UserResponse userLogin(Login login) {
        System.out.println("***** userLogin function is starting *****");

        Users dbUser = null;

        if (login != null) {
            System.out.println("checking whether the user is available");
            // check whether the user is available in the database
            dbUser = userRepository.findByUserEmail(login.getUserEmail());

            // if the user found in the database
            if (dbUser != null) {
                System.out.println("user is available. therefore, matching the passwords");
                // matching the password
                if (login.getUserPassword().equals(dbUser.getUserPassword())) {
                    System.out.println("passwords are matching. login succeeded");
                    // passwords are matching
                    userResponse.setResponseStatus(200);
                    userResponse.setResponseDescription("User logged in successfully");
                    userResponse.setUsers(dbUser);
                }
                else {
                    System.out.println("incorrect password");
                    // passwords do not match
                    userResponse.setResponseStatus(400);
                    userResponse.setResponseDescription("Incorrect password");
                    userResponse.setUsers(null);
                }
            }
            else {
                System.out.println("user not available. may be the user email is incorrect");
                // user does not found in the database (incorrect username)
                userResponse.setResponseStatus(400);
                userResponse.setResponseDescription("Incorrect username");
                userResponse.setUsers(null);
            }
        }
        return userResponse;
    }

    /**
     * function to get all the details of the given department
     * @return
     */
    public List<Users> getDepartmentUsers(int departmentId) {
        System.out.println("***** getDepartmentUsers function is starting *****");
        List<Users> dbUsers = new ArrayList<>();

        if (departmentId == 0) {
            System.out.println("Invalid department id");
        }
        else {
            System.out.println("Retrieving all the users from the database");

            try {
                dbUsers = userRepository.findAllByDepartmentId(departmentId);
                System.out.println("Users retrieved successfully");

                if (dbUsers.size() == 0) {
                    System.out.println("No users found in the given department");
                }
            } catch (Exception ex) {
                System.out.println("User retrieving failed!");
                ex.printStackTrace();
            }
        }
        return dbUsers;
    }
}
