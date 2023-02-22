package com.inn.jewelry.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
/**

 This class represents a Product entity, which is mapped to the "product" table in the database.
 It contains attributes that represent the details of a product, such as the product's ID, name, description, price, status,
 and category ID and name. The class is annotated with Lombok's @Data annotation for automatic generation of getters, setters,
 and other convenience methods.
 It also includes Hibernate annotations for dynamic insert and update, as well as four named queries to retrieve all products,
 update the status of a product, retrieve products by category ID, and retrieve a product by its ID, respectively. The queries
 use the wrapper class "ProductWrapper" to return a subset of product attributes, instead of the full Product entity.
 */

/**

 This named query retrieves all products in the system, with a subset of their attributes encapsulated in the "ProductWrapper" class.
 It is used to retrieve all products in the system, and returns only a subset of product attributes to optimize the loading of data.
 */
@NamedQuery(name = "Product.getAllProduct", query = "select new com.inn.jewelry.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p")

/**

 This named query updates the status of a product with a given ID.
 It is used to update the status of a product to "true" or "false", based on whether the product is available for purchase or not.
 The query takes two parameters: "status" to indicate the new status of the product, and "id" to specify the product to update.
 */
@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=:id")

/**

 This named query retrieves all products belonging to a specific category, with a subset of their attributes encapsulated in the "ProductWrapper" class.
 It is used to retrieve all products belonging to a specific category, and returns only a subset of product attributes to optimize the loading of data.
 The query takes a parameter "id" which is used to filter the products by the category ID.
 */
@NamedQuery(name = "Product.getProductByCategory", query = "select new com.inn.jewelry.wrapper.ProductWrapper(p.id,p.name) from Product p where p.category.id=:id and p.status='true'")

/**

 This named query retrieves a product with a given ID, with a subset of its attributes encapsulated in the "ProductWrapper" class.
 It is used to retrieve a specific product by its ID, and returns only a subset of product attributes to optimize the loading of data.
 The query takes a parameter "id" which is used to filter the products by the product ID.
 */
@NamedQuery(name = "Product.getProductById", query = "select new com.inn.jewelry.wrapper.ProductWrapper(p.id,p.name,p.description,p.price) from Product p where p.id=:id")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUId = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status;

}
