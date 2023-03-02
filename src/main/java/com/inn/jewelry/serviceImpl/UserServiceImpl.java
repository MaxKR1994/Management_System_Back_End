package com.inn.jewelry.serviceImpl;

import com.inn.jewelry.JWT.CustomerUserDetailsService;
import com.inn.jewelry.JWT.JwtFilter;
import com.inn.jewelry.JWT.JwtUtil;
import com.inn.jewelry.POJO.User;
import com.inn.jewelry.constants.StoreConstants;
import com.inn.jewelry.dao.UserDao;
import com.inn.jewelry.service.UserService;
import com.inn.jewelry.utils.EmailUtils;
import com.inn.jewelry.utils.StoreUtils;
import com.inn.jewelry.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    EmailUtils emailUtils;

    /**
     * Registers a new user.
     *
     * @param requestMap a map containing user details like name, contact number, email and password
     * @return a ResponseEntity containing a success message if the user was registered successfully,
     * an error message if the email already exists or the request is invalid
     */
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signUp{}",requestMap);
        try {
            if(validateSignUpMap(requestMap)){
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap));
                    return StoreUtils.getResponseEntity(StoreConstants.SUCCESSFULLY_REGISTERED,HttpStatus.OK);
                } else {
                    return StoreUtils.getResponseEntity(StoreConstants.EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
                }
            } else {
                return StoreUtils.getResponseEntity(StoreConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Validates whether the given map contains required fields for signing up a user.
     *
     * @param requestMap a map containing user details
     * @return true if the map contains required fields, false otherwise
     */
    private boolean validateSignUpMap(Map<String,String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a User object from the given map.
     *
     * @param requestMap a map containing user details
     * @return a User object with the given user details
     */
    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    /**
     * Logs in a user.
     *
     * @param requestMap a map containing user details like email and password
     * @return a ResponseEntity containing a JWT token if the login was successful, an error message otherwise
     */
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if(auth.isAuthenticated()){
                if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                                    customerUserDetailsService.getUserDetail().getRole())+"\"}",
                    HttpStatus.OK);
                } else{
                    return new ResponseEntity<String>("{\"message\":\""+
                            "Wait for admin approval. "+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception ex){
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+
                "Bad Credentials. "+"\"}",HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets all users.
     *
     * @return a ResponseEntity containing a list of UserWrapper objects representing all users
     * if the user making the request is an admin, an error message otherwise
     */
    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{
            if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Updates the status of a user.
     *
     * @param requestMap a map containing user ID and status
     * @return a ResponseEntity containing a success message if the user status was updated successfully,
     * an error message if the user doesn't exist, the request is unauthorized, or an error occurred
     */
    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
               Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
               if (!optional.isEmpty()){
                    userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(),userDao.getAllAdmin());
                    return StoreUtils.getResponseEntity(StoreConstants.USER_STATUS_UPDATED_SUCCESSFULLY,HttpStatus.OK);
               }else {
                   StoreUtils.getResponseEntity(StoreConstants.USER_DOESNT_EXIST,HttpStatus.OK);
               }
            }else {
                return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
            } catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Sends an email notification to all admins after updating a user's status.
     *
     * @param status the new status of the user
     * @param user the email of the user whose status was updated
     * @param allAdmin a list of all admin email addresses
     */
    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status != null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),StoreConstants.ACCOUNT_APPROVED,"USER:- " + user + "\n is approved by \nADMIN:= " + jwtFilter.getCurrentUser(),allAdmin);
        }else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),StoreConstants.ACCOUNT_DISABLED,"USER:- " + user + "\n is disabled by \nADMIN:= " + jwtFilter.getCurrentUser(),allAdmin);
        }
    }

    /**
     * Checks if the JWT token is valid.
     *
     * @return a ResponseEntity containing an OK status if the token is valid,
     * an UNAUTHORIZED status otherwise
     */
    @Override
    public ResponseEntity<String> checkToken() {
        return StoreUtils.getResponseEntity("true", HttpStatus.OK);
    }

    /**
     * Changes the password for the user with the given username and returns a boolean indicating
     * whether the password was successfully changed.
     *
     * @param requestMap a map containing the username, current password, and new password
     * @return true if the password was successfully changed, false otherwise
     */
    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (!userObj.equals(null)){
                if (userObj.getPassword().equals(requestMap.get("oldPassword"))){
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return StoreUtils.getResponseEntity(StoreConstants.PASSWORD_UPDATED_SUCCESSFULLY,HttpStatus.OK);
                }
                return StoreUtils.getResponseEntity(StoreConstants.INCORRECT_OLD_PASSWORD,HttpStatus.BAD_REQUEST);
            }
            return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Sends a password reset email to the user with the given email address.
     *
     * @param requestMap a map containing the user's email address
     * @return true if the email was sent successfully, false otherwise
     */
    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            User user = userDao.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(user) && !Strings.isNotEmpty(user.getEmail())){
                emailUtils.forgotMail(user.getEmail(),StoreConstants.CREDENTIALS_FOR_STORE_MANAGEMENT_SYSTEM,user.getPassword());
            }
            return StoreUtils.getResponseEntity(StoreConstants.CHECK_YOUR_EMAIL_FOR_CREDENTIALS,HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
