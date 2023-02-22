package com.inn.jewelry.rest;

import com.inn.jewelry.POJO.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 This is an interface called CategoryRest which is used to define the endpoints for handling
 Category-related operations in the REST API of a jewelry management system.

 The interface defines three methods:

 addNewCategory - used to add a new category to the system.
 getAllCategory - used to retrieve all categories from the system, optionally filtered by a filterValue parameter.
 updateCategory - used to update an existing category in the system.
 Each method is mapped to a specific HTTP endpoint using the @PostMapping,
 @GetMapping, and @PutMapping annotations, respectively.
 The @RequestBody annotation is used to indicate that the method expects a request body
 containing data to be passed to the method, and the @RequestParam annotation is used
 to specify query parameters that can be passed in the URL of a GET request.
 The @ResponseBody annotation is not used because the return type of each method is already wrapped
 in a ResponseEntity object, which allows for more control over the response status code and headers.
 */

/**
 For the @RequestMapping annotation in the CategoryRest interface,
 it specifies the base URI path for all the methods in this interface.
 */
@RequestMapping(path = "/category")
public interface CategoryRest {

    /**
     * Endpoint to add a new category.
     *
     * For the @PostMapping annotation on the addNewCategory method, it maps HTTP POST requests to the specified URI path.
     *
     * For the @RequestBody annotation on the addNewCategory and updateCategory methods,
     * it indicates that the request body should be converted to the specified Java object.
     *
     * @param requestMap Map containing the category details.
     * @return ResponseEntity with success/failure message.
     */
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String,String> requestMap);

    /**
     * Endpoint to get all the categories.
     *
     * For the @GetMapping annotation on the getAllCategory method,
     * it maps HTTP GET requests to the specified URI path.
     *
     * For the @RequestParam annotation on the getAllCategory method,
     * it indicates that the specified query parameter is required and should be bound to the method parameter.
     *
     * @param filterValue Optional filter value to filter categories.
     * @return ResponseEntity with the list of categories.
     */
    @GetMapping(path="/get")
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue);

    /**
     * Endpoint to update an existing category.
     *
     * For the @PutMapping annotation on the updateCategory method,
     * it maps HTTP PUT requests to the specified URI path.
     *
     * @param requestMap Map containing the updated category details.
     * @return ResponseEntity with success/failure message.
     */
    @PutMapping(path="/update")
    ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String,String> requestMap);
}
