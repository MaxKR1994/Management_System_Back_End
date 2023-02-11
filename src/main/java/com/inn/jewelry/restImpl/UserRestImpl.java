package com.inn.jewelry.restImpl;

import com.inn.jewelry.constents.StoreConstants;
import com.inn.jewelry.rest.UserRest;
import com.inn.jewelry.service.UserService;
import com.inn.jewelry.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;
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
}
