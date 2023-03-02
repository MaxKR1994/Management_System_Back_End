package com.inn.jewelry.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
/**
 * Represents a user in the system.
 */

/**
 * Retrieves a user by their email address.
 *
 * param email the email address to search for
 * @return the User object matching the given email address, or null if no match is found
 */
@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email =: email")

/**
 * Retrieves a list of UserWrapper objects for all users with the "user" role.
 * @return a list of UserWrapper objects for all users with the "user" role
 */
@NamedQuery(name = "User.getAllUser", query = "select new com.inn.jewelry.wrapper.UserWrapper(u.id, u.name, u.email, u.contactNumber, u.status) from User u where u.role='user'")

/**
 * Updates the status of a user by their ID.
 *
 * @param id     the ID of the user to update
 * @param status the new status for the user
 * @return the number of rows updated
 */
@NamedQuery(name = "User.updateStatus", query = "update User u set u.status=:status where u.id=:id")

/**
 * Retrieves the email address of all users with the "admin" role.
 *
 * @return a list of email addresses for all users with the "admin" role
 */
@NamedQuery(name = "User.getAllAdmin", query = "select u.email from User u where u.role='admin'")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "contactNumber")
    private String contactNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private String status;
    @Column(name = "role")
    private String role;

}
