package com.inn.jewelry.serviceImpl;

import com.inn.jewelry.constents.StoreConstants;
import com.inn.jewelry.service.UserService;
import com.inn.jewelry.utils.StoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signUp{}",requestMap);
        if(validateSignUp(requestMap)){

        } else {
            return StoreUtils.getResponseEntity(StoreConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    private boolean validateSignUp(Map<String ,String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        else {
            return false;
        }
    }
}
