package com.inn.jewelry.JWT;


import com.inn.jewelry.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

/**

 This class implements UserDetailsService interface to load user details by username.
 It retrieves user details from UserDao and returns UserDetails object.
 */

/**

 The @Slf4j annotation is used to automatically generate an implementation
 of the org.slf4j.Logger interface at compile time, which can be used
 for logging within the annotated class.
 */
@Slf4j
/**

 TThe @Service annotation is used to indicate that a class is a Spring-managed service bean.
 It is used to indicate that the annotated class performs
 some business logic and is eligible for dependency injection.
 */
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    /**

     The @Autowired annotation is used in Spring to automatically inject dependencies
     into a class. When a class has a field or constructor annotated with @Autowired,
     Spring will search for a bean of the corresponding type and automatically wire it into the class.
     This can greatly simplify dependency management and make the code more modular and easy to understand.
     */
    @Autowired
    UserDao userDao;

    private com.inn.jewelry.POJO.User userDetail;

    /**
     * This method loads user details by username.
     *
     * @param username - The email id of the user to be loaded.
     * @return - UserDetails object containing user details.
     * @throws UsernameNotFoundException if the user with the given email id is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUserName {}", username);
        userDetail = userDao.findByEmailId(username);
        if(!Objects.isNull(userDetail)){
            return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found. (");
        }
    }

    /**
     * This method returns the user details.
     *
     * @return - User object containing user details.
     */
    public com.inn.jewelry.POJO.User getUserDetail(){
        return userDetail;
    }

}
