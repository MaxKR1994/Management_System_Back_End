package com.inn.jewelry.dao;

import com.inn.jewelry.POJO.User;
import com.inn.jewelry.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
/**

 Interface for accessing user-related database operations using Spring Data JPA.
 */
public interface UserDao extends JpaRepository<User,Integer> {

    /**
     * Find a user by email id.
     * @param email the email id to search for
     * @return the user with the matching email id or null if not found
     */
    User findByEmailId(@Param("email") String email);

    /**
     * Get a list of all users.
     * @return a list of user objects containing only selected fields
     */
    List<UserWrapper> getAllUser();

    /**
     * Update the status of a user.
     * @param status the new status to set
     * @param id the id of the user to update
     * @return the number of affected rows
     */
    @Transactional
    @Modifying
    Integer updateStatus(@Param("status")String status,@Param("id") Integer id);

    /**
     * Get a list of all admin users.
     * @return a list of email addresses for all admin users
     */
    List<String> getAllAdmin();

    /**
     * Find a user by email.
     * @param email the email to search for
     * @return the user with the matching email or null if not found
     */
    User findByEmail(String email);


}
