package com.inn.jewelry.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 This class represents a Category entity, which is mapped to the "category" table in the database.
 It contains the category's ID and name as attributes, and is annotated with Lombok's @Data annotation
 for automatic generation of getters, setters, and other convenience methods.
 It also includes Hibernate annotations for dynamic insert and update, as well as a named query to retrieve
 all categories that have at least one product with status "true".
 */

/**
 This named query retrieves all categories that have at least one product with status "true".
 It is used to optimize the loading of categories by avoiding loading categories without any active products.
 */

@NamedQuery(name = "Category.getAllCategory", query = "select c from Category c where c.id in (select p.category from Product p where p.status = 'true' )")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

}
