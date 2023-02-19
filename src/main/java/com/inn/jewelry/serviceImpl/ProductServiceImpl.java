package com.inn.jewelry.serviceImpl;

import com.inn.jewelry.JWT.JwtFilter;
import com.inn.jewelry.POJO.Category;
import com.inn.jewelry.POJO.Product;
import com.inn.jewelry.constents.StoreConstants;
import com.inn.jewelry.dao.ProductDao;
import com.inn.jewelry.service.ProductService;
import com.inn.jewelry.utils.StoreUtils;
import com.inn.jewelry.wrapper.ProductWrapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

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

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try{
            return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

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
