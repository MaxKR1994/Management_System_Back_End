package com.inn.jewelry.serviceImpl;

import com.inn.jewelry.JWT.JwtFilter;
import com.inn.jewelry.POJO.Category;
import com.inn.jewelry.POJO.Product;
import com.inn.jewelry.constants.StoreConstants;
import com.inn.jewelry.dao.ProductDao;
import com.inn.jewelry.service.ProductService;
import com.inn.jewelry.utils.StoreUtils;
import com.inn.jewelry.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 This is a Java class implementing the ProductService interface.
 The class provides implementations for methods that handle product-related functionality in a jewelry store.

 The class uses Spring Framework annotations such as @Autowired and @Service to enable dependency injection and component scanning.
 It also makes use of a JWT filter to authorize access to certain endpoints based on user roles.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

    /**
     addNewProduct: This method is used to add a new product to the store.
     It validates the input request, checks the user's role, and returns an appropriate response.
     */
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validaiteProductMap(requestMap,false)){
                    productDao.save(getProductMap(requestMap,false));
                    return StoreUtils.getResponseEntity(StoreConstants.PRODUCT_ADDED_SUCCESSFULLY,HttpStatus.OK);
                }
                return StoreUtils.getResponseEntity(StoreConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else {
                return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     getAllProduct: This method returns a list of all products in the store.
     */
    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try{
            return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     updateProduct: This method is used to update an existing product.
     It validates the input request, checks the user's role, and returns an appropriate response.
     */
    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validaiteProductMap(requestMap,true)){
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        Product product = getProductMap(requestMap,true);
                        product.setStatus(optional.get().getStatus());
                        productDao.save(getProductMap(requestMap,true));
                        return StoreUtils.getResponseEntity(StoreConstants.PRODUCT_UPDATED_SUCCESSFULLY,HttpStatus.OK);
                    }else {
                        return StoreUtils.getResponseEntity(StoreConstants.PRODUCT_ID_DOES_NOT_EXIST,HttpStatus.OK);
                    }
                } else {
                    return StoreUtils.getResponseEntity(StoreConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
                }
            } else {
                return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     deleteProduct: This method is used to delete an existing product.
     It checks the user's role and returns an appropriate response.
     */
    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            if(jwtFilter.isAdmin()){
                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()){
                    productDao.deleteById(id);
                    return StoreUtils.getResponseEntity(StoreConstants.PRODUCT_DELETED_SUCCESSFULLY,HttpStatus.OK);
                }else {
                    return StoreUtils.getResponseEntity(StoreConstants.PRODUCT_ID_DOES_NOT_EXIST,HttpStatus.OK);
                }
            } else {
                return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     updateStatus: This method is used to update the status of an existing product.
     It checks the user's role and returns an appropriate response.
     */
    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    productDao.updateProductStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    return StoreUtils.getResponseEntity(StoreConstants.PRODUCT_STATUS_UPDATED_SUCCESSFULLY,HttpStatus.OK);
                }else {
                    return StoreUtils.getResponseEntity(StoreConstants.PRODUCT_ID_DOES_NOT_EXIST,HttpStatus.OK);
                }
            } else {
                return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     getByCategory: This method returns a list of products in a given category.
     */
    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{
            return new ResponseEntity<>(productDao.getProductByCategory(id),HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     getByCategory: This method returns a list of products in a given category.
     */
    @Override
    public ResponseEntity<List<ProductWrapper>> getById(Integer id) {
        try{
            return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     This method validates the product map and checks if it contains name and id.
     If validateId is true, it also checks if the id is present.
     Returns true if the map is valid, false otherwise.
     */
    private boolean validaiteProductMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    /**
     This method generates a Product object from the request map.
     If isAdd is true, it sets the product ID to the one in the request map.
     Otherwise, it sets the product status to "true".
     It sets other properties of the Product object from the request map and returns it.
     */
    private Product getProductMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        Product product = new Product();
        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            product.setStatus("true");
        }

        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));

        return product;
    }
}
