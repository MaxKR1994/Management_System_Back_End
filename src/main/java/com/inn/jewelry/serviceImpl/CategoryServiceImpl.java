package com.inn.jewelry.serviceImpl;

import com.inn.jewelry.JWT.JwtFilter;
import com.inn.jewelry.POJO.Category;
import com.inn.jewelry.constants.StoreConstants;
import com.inn.jewelry.dao.CategoryDao;
import com.inn.jewelry.service.CategoryService;
import com.inn.jewelry.utils.StoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**

 This class implements the CategoryService interface and provides the implementation of various operations related to
 category management such as adding a new category, retrieving all categories, and updating an existing category.
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    /**

     Adds a new category to the system.
     @param requestMap a map containing the category data, such as name and ID.
     @return ResponseEntity a response containing the status of the operation.
     */
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if (validateCategoryMap(requestMap,false)){
                    categoryDao.save(getCategoryFromMap(requestMap,false));
                    return StoreUtils.getResponseEntity(StoreConstants.CATEGORY_ADDED_SUCCESSFULLY,HttpStatus.OK);
                }
            }else {
                return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**

     Validates a category map to ensure that it contains the necessary data.
     @param requestMap a map containing the category data, such as name and ID.
     @param validateId a flag indicating whether to validate the ID field.
     @return boolean true if the map is valid, false otherwise.
     */
    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId){
                return true;
            }
        }
        return false;
    }

    /**
     * Constructs a new Category object from the given map.
     *
     * @param requestMap the map containing the category data
     * @param isAdd      a boolean indicating if this is a new category being added
     * @return a new Category object constructed from the given map
     */
    private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    /**

     Retrieves all categories in the system.
     @param filterValue a filter value to narrow down the results.
     @return ResponseEntity a response containing the list of categories.
     */
    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            if(!Strings.isEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**

     Updates an existing category in the system.
     @param requestMap a map containing the category data, such as name and ID.
     @return ResponseEntity a response containing the status of the operation.
     */
    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateCategoryMap(requestMap,true)){
                    Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        categoryDao.save(getCategoryFromMap(requestMap,true));
                        return StoreUtils.getResponseEntity(StoreConstants.CATEGORY_UPDATED_SUCCESSFULLY,HttpStatus.OK);
                    }else {
                        return StoreUtils.getResponseEntity(StoreConstants.CATEGORY_ID_DOES_NOT_EXIST,HttpStatus.OK);
                    }
                }
                return StoreUtils.getResponseEntity(StoreConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            } else {
                return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
