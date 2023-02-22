package com.inn.jewelry.restImpl;

import com.inn.jewelry.constants.StoreConstants;
import com.inn.jewelry.rest.UserRest;
import com.inn.jewelry.service.UserService;
import com.inn.jewelry.utils.StoreUtils;
import com.inn.jewelry.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of UserRest interface for handling user-related API requests.
 */
@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    /**
     * Handle sign up request for creating new user account.
     * @param requestMap Request body as Map<String, String> containing user details.
     * @return ResponseEntity with success message if user is successfully registered, or error message if registration failed.
     */
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{
            ResponseEntity<String> response = userService.signUp(requestMap);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return StoreUtils.getResponseEntity(StoreConstants.SUCCESSFULLY_REGISTERED, HttpStatus.OK);
            } else {
                return response;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handle login request for existing user account.
     * @param requestMap Request body as Map<String, String> containing user credentials.
     * @return ResponseEntity with user token if login is successful, or error message if login failed.
     */
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle get all user request.
     * @return ResponseEntity with list of all users in UserWrapper format, or error message if request failed.
     */
    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            return userService.getAllUser();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle update user request.
     * @param requestMap Request body as Map<String, String> containing updated user details.
     * @return ResponseEntity with success message if user is successfully updated, or error message if update failed.
     */
    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            return userService.update(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle check token request for validating user token.
     * @return ResponseEntity with success message if token is valid, or error message if validation failed.
     */
    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle change password request for existing user account.
     * @param requestMap Request body as Map<String, String> containing user credentials and new password.
     * @return ResponseEntity with success message if password is successfully changed, or error message if change failed.
     */
    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            return userService.changePassword(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method is used to handle forgot password request of user.
     * It takes a map of request parameters including email and generates a password reset link which is sent to the user's email.
     * If successful, returns a response entity with success message, otherwise returns a response entity with error message and HTTP status code.
     *
     * @param requestMap A map of request parameters including email.
     * @return A response entity containing success message if password reset link was sent successfully, otherwise containing error message and HTTP status code.
     */
    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            return userService.forgotPassword(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
