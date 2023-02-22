package com.inn.jewelry.service;

import com.inn.jewelry.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**

 This interface defines the methods for performing CRUD operations on products.
 */
public interface ProductService {

    /**
     * Adds a new product to the database.
     *
     * @param requestMap A map containing the information of the new product.
     * @return A ResponseEntity indicating if the product was added successfully or not.
     */
    ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

    /**
     * Retrieves a list of all products from the database.
     *
     * @return A ResponseEntity containing a list of ProductWrapper objects.
     */
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    /**
     * Updates an existing product in the database.
     *
     * @param requestMap A map containing the updated information of the product.
     * @return A ResponseEntity indicating if the product was updated successfully or not.
     */
    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

    /**
     * Deletes an existing product from the database.
     *
     * @param id The ID of the product to be deleted.
     * @return A ResponseEntity indicating if the product was deleted successfully or not.
     */
    ResponseEntity<String> deleteProduct(Integer id);

    /**
     * Updates the status of an existing product in the database.
     *
     * @param requestMap A map containing the updated status of the product.
     * @return A ResponseEntity indicating if the product status was updated successfully or not.
     */
    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    /**
     * Retrieves a list of products by category from the database.
     *
     * @param id The ID of the category to filter products by.
     * @return A ResponseEntity containing a list of ProductWrapper objects filtered by category.
     */
    ResponseEntity<List<ProductWrapper>> getByCategory(Integer id);

    /**
     * Retrieves a product by ID from the database.
     *
     * @param id The ID of the product to retrieve.
     * @return A ResponseEntity containing a ProductWrapper object.
     */
    ResponseEntity<List<ProductWrapper>> getById(Integer id);
}
