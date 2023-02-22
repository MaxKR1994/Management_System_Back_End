package com.inn.jewelry.rest;

import com.inn.jewelry.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**

 This interface defines the REST endpoints for Product management.
 It includes methods for adding, getting, updating, and deleting Products,
 as well as updating the status of a Product and retrieving Products by category or id.
 */
@RequestMapping(path = "/product")
public interface ProductRest {

    /**
     * REST endpoint to add a new product.
     * @param requestMap - A map containing the required information for the new product.
     * @return - A ResponseEntity indicating whether the operation was successful or not.
     */
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String,String> requestMap);

    /**
     * REST endpoint to get all products.
     * @return - A ResponseEntity containing a list of ProductWrappers.
     */
    @GetMapping(path="/get")
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    /**
     This method updates an existing product using a PUT HTTP request.
     It expects a request body containing a Map object with key-value pairs of product attributes to be updated.
     It returns a ResponseEntity with a message string indicating whether the update was successful or not.
     */
    @PutMapping(path="/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String,String> requestMap);

    /**
     This method deletes an existing product using a DELETE HTTP request.
     It expects the id of the product to be deleted as a path variable.
     It returns a ResponseEntity with a message string indicating whether the deletion was successful or not.
     */
    @DeleteMapping(path="/delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    /**
     This method updates the status of a product using a PUT HTTP request.
     It expects a request body containing a Map object with key-value pairs of the product ID and the new status value.
     It returns a ResponseEntity with a message string indicating whether the update was successful or not.
     */
    @PutMapping(path="/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String,String> requestMap);

    /**
     This method retrieves a list of products by their category ID using a GET HTTP request.
     It expects the category ID as a path variable.
     It returns a ResponseEntity containing a list of ProductWrapper objects.
     */
    @GetMapping(path="/getByCategory/{id}")
    ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);

    /**
     This method retrieves a product by its ID using a GET HTTP request.
     It expects the product ID as a path variable.
     It returns a ResponseEntity containing a list of ProductWrapper objects (which should contain only one product).
     */
    @GetMapping(path="/getById/{id}")
    ResponseEntity<List<ProductWrapper>> getById(@PathVariable Integer id);

}
