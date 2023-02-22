package com.inn.jewelry.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 This class ProductWrapper is a simple Java bean class that encapsulates the data of a product in a structured way.
 It is used as a wrapper for the Product entity class to provide a simplified representation of the product data to the front-end UI.
 The class contains private fields for the various attributes of a product,
 along with corresponding getters and setters for accessing and modifying the values of these attributes.
 It also includes several constructors for initializing the object with different combinations of data.

 Overall, the ProductWrapper class is a data transfer object (DTO)
 that provides a convenient and standardized way of passing product data between different parts of the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWrapper {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String status;
    private Integer categoryId;
    private String categoryName;

    public ProductWrapper(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(Integer id, String name, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
