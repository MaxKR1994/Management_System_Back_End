package com.inn.jewelry.service;

import com.inn.jewelry.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**

 The UserService interface provides methods to perform operations related to user management.
 */
public interface UserService {

    /**
     * This method registers a new user.
     * @param requestMap the request body containing user details.
     * @return ResponseEntity with a message indicating success or failure.
     */
    ResponseEntity<String> signUp(Map<String,String> requestMap);

    /**
     * This method logs in a user.
     * @param requestMap the request body containing user credentials.
     * @return ResponseEntity with a JWT token if login is successful.
     */
    ResponseEntity<String> login(Map<String,String> requestMap);

    /**
     * This method retrieves all users.
     * @return ResponseEntity with a list of UserWrapper objects.
     */
    ResponseEntity<List<UserWrapper>> getAllUser();

    /**
     * This method updates user details.
     * @param requestMap the request body containing user details.
     * @return ResponseEntity with a message indicating success or failure.
     */
    ResponseEntity<String> update(Map<String,String> requestMap);

    /**
     * This method checks if the JWT token provided in the header is valid.
     * @return ResponseEntity with a message indicating the token is valid or not.
     */
    ResponseEntity<String> checkToken();

    /**
     * This method updates the password for the currently logged in user.
     * @param requestMap the request body containing old and new passwords.
     * @return ResponseEntity with a message indicating success or failure.
     */
    ResponseEntity<String> changePassword(Map<String,String> requestMap);

    /**
     * This method sends an email to the user with a link to reset their password.
     * @param requestMap the request body containing user email.
     * @return ResponseEntity with a message indicating success or failure.
     */
    ResponseEntity<String> forgotPassword(Map<String,String> requestMap);

}
