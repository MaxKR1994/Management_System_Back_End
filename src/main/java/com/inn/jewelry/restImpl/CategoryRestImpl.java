package com.inn.jewelry.restImpl;

import com.inn.jewelry.POJO.Category;
import com.inn.jewelry.constants.StoreConstants;
import com.inn.jewelry.rest.CategoryRest;
import com.inn.jewelry.service.CategoryService;
import com.inn.jewelry.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**

 This class implements the CategoryRest interface and provides the REST API endpoints for managing categories.
 */
@RestController
public class CategoryRestImpl implements CategoryRest {

    @Autowired
    CategoryService categoryService;

    /**

     This method handles the REST API endpoint for adding a new category.
     @param requestMap A map containing the request parameters.
     @return A ResponseEntity object containing the response message and status code.
     */
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            return categoryService.addNewCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**

     This method handles the REST API endpoint for getting all categories.
     @param filterValue A filter value for filtering the categories.
     @return A ResponseEntity object containing the list of categories and status code.
     */
    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            return categoryService.getAllCategory(filterValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**

     This method handles the REST API endpoint for updating a category.
     @param requestMap A map containing the request parameters.
     @return A ResponseEntity object containing the response message and status code.
     */
    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            return categoryService.updateCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
