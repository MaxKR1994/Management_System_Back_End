package com.inn.jewelry.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
/**

 This class represents a Bill entity, which is mapped to the "bill" table in the database.
 It contains attributes that represent the details of a bill, such as the bill's ID, UUID, name, email, contact number,
 payment method, total amount, product details, and the username of the user who created the bill.
 The class is annotated with Lombok's @Data annotation for automatic generation of getters, setters, and other convenience methods.
 It also includes Hibernate annotations for dynamic insert and update, as well as two named queries to retrieve all bills and bills
 created by a specific user, respectively.
 */

/**

 This named query retrieves all bills in descending order of their ID.
 It is used to retrieve all bills in the system, with the latest bills appearing first in the result set.
 */
@NamedQuery(name = "Bill.getAllBills", query = "select b from Bill b order by b.id desc ")

/**

 This named query retrieves all bills created by a specific user in descending order of their ID.
 It is used to retrieve all bills created by a specific user, with the latest bills appearing first in the result set.
 The query takes a parameter "username" which is used to filter the bills by the user who created them.
 */
@NamedQuery(name = "Bill.getBillByUserName", query = "select b from Bill b where b.createdBy=:username order by b.id desc")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "bill")
public class Bill implements Serializable {

    private static final long serialVersionUId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "total")
    private Integer total;

    @Column(name = "productDetails",columnDefinition = "json")
    private String productDetails;

    @Column(name = "createdBy")
    private String createdBy;
}
