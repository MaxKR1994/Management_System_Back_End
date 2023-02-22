package com.inn.jewelry.rest;

import com.inn.jewelry.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**

 This interface represents RESTful API endpoints for managing users.
 */
@RequestMapping(path = "/user")
public interface UserRest {

    /**
     * Sign up a new user with the given credentials.
     *
     * @param requestMap a map containing the user's username, password and email address
     * @return a response entity with a string message indicating the result of the operation
     */
    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String,String> requestMap);

    /**
     * Log in an existing user with the given credentials.
     *
     * @param requestMap a map containing the user's username and password
     * @return a response entity with a string message indicating the result of the operation
     */
    @PostMapping(path = "/login")
    public ResponseEntity<String> login (@RequestBody(required = true) Map<String,String> requestMap);

    /**
     * Retrieve a list of all users.
     *
     * @return a response entity with a list of UserWrapper objects representing all users
     */
    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUser();

    /**
     * Update an existing user's information.
     *
     * @param requestMap a map containing the user's updated information
     * @return a response entity with a string message indicating the result of the operation
     */
    @PostMapping(path = "/update")
    public ResponseEntity<String> update (@RequestBody(required = true) Map<String,String> requestMap);

    /**
     * Check whether the current token is still valid.
     *
     * @return a response entity with a string message indicating the result of the operation
     */
    @GetMapping(path = "/checkToken")
    ResponseEntity<String> checkToken();

    /**
     * Change an existing user's password.
     *
     * @param requestMap a map containing the user's current and new password
     * @return a response entity with a string message indicating the result of the operation
     */
    @PostMapping(path = "/changePassword")
    ResponseEntity<String> changePassword (@RequestBody Map<String,String> requestMap);

    /**
     * Send a password reset email to the user's email address.
     *
     * @param requestMap a map containing the user's email address
     * @return a response entity with a string message indicating the result of the operation
     */
    @PostMapping(path = "/forgotPassword")
    ResponseEntity<String> forgotPassword (@RequestBody Map<String,String> requestMap);

}
