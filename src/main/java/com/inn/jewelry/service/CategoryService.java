package com.inn.jewelry.service;

import com.inn.jewelry.POJO.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**

 The CategoryService interface provides methods for managing categories in the system.
 */
public interface CategoryService {

    /**
     * Adds a new category with the given details to the system.
     *
     * @param requestMap a map containing the details of the category to be added.
     * @return a ResponseEntity containing a success or error message.
     */
    ResponseEntity<String> addNewCategory(Map<String,String> requestMap);

    /**
     * Retrieves a list of all categories from the system based on the filter value.
     *
     * @param filterValue a filter value to search for a specific category.
     * @return a ResponseEntity containing a list of Category objects.
     */
    ResponseEntity<List<Category>> getAllCategory(String filterValue);

    /**
     * Updates an existing category with the given details in the system.
     *
     * @param requestMap a map containing the details of the category to be updated.
     * @return a ResponseEntity containing a success or error message.
     */
    ResponseEntity<String> updateCategory(Map<String, String> requestMap);
}
