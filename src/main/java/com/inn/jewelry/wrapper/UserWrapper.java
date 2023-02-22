package com.inn.jewelry.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**

 This class represents a wrapper for the User model, used to transfer data between different parts of the application.

 UserWrapper(Integer id, String name, String email, String contactnumber, String status): creates a new UserWrapper object with the provided attributes.
 Note: Lombok annotations @Data and @NoArgsConstructor are used to automatically generate getters, setters, and constructors for this class.
 */
@Data
@NoArgsConstructor
public class UserWrapper {
    private Integer id;
    private String name;
    private String email;
    private String contactnumber;
    private String status;

    public UserWrapper(Integer id, String name, String email, String contactnumber, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactnumber = contactnumber;
        this.status = status;
    }
}
