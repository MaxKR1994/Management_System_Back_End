package com.inn.jewelry.restImpl;

import com.inn.jewelry.constents.StoreConstants;
import com.inn.jewelry.rest.ProductRest;
import com.inn.jewelry.service.ProductService;
import com.inn.jewelry.utils.StoreUtils;
import com.inn.jewelry.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**

 ProductRestImpl class is responsible for handling REST API requests related to product management.
 */
@RestController
public class ProductRestImpl implements ProductRest {

    @Autowired
    ProductService productService;

    /**
     * This method is responsible for adding a new product to the store.
     * @param requestMap The request body of the API call which contains the details of the product to be added.
     * @return The HTTP response containing the status of the operation.
     */
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            return productService.addNewProduct(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method is responsible for fetching all the products in the store.
     * @return The HTTP response containing the list of all products in the store.
     */
    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try{
            return productService.getAllProduct();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method is responsible for updating the details of a product in the store.
     * @param requestMap The request body of the API call which contains the updated details of the product.
     * @return The HTTP response containing the status of the operation.
     */
    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            return productService.updateProduct(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method is responsible for deleting a product from the store.
     * @param id The ID of the product to be deleted.
     * @return The HTTP response containing the status of the operation.
     */
    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            return productService.deleteProduct(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method is responsible for updating the status of a product in the store.
     * @param requestMap The request body of the API call which contains the ID and updated status of the product.
     * @return The HTTP response containing the status of the operation.
     */
    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            return productService.updateStatus(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Retrieve a list of ProductWrappers corresponding to the Products in the specified Category.
     *
     * @param id the id of the Category to retrieve Products for
     * @return a ResponseEntity containing a list of ProductWrappers or an empty list and an HTTP status code
     */
    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{
            return productService.getByCategory(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Retrieve a list of ProductWrappers corresponding to the Product with the specified id.
     *
     * @param id the id of the Product to retrieve
     * @return a ResponseEntity containing a list of ProductWrappers or an empty list and an HTTP status code
     */
    @Override
    public ResponseEntity<List<ProductWrapper>> getById(Integer id) {
        try{
            return productService.getById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
