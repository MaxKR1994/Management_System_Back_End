package com.inn.jewelry.serviceImpl;

import com.inn.jewelry.POJO.User;
import com.inn.jewelry.constents.StoreConstants;
import com.inn.jewelry.dao.UserDao;
import com.inn.jewelry.service.UserService;
import com.inn.jewelry.utils.StoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

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

    private boolean validateSignUpMap(Map<String,String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactnumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        } else {
            return false;
        }
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactnumber(requestMap.get("contactnumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

}
