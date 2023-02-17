package com.inn.jewelry.serviceImpl;

import com.inn.jewelry.JWT.JwtFilter;
import com.inn.jewelry.POJO.Category;
import com.inn.jewelry.constents.StoreConstants;
import com.inn.jewelry.dao.CategoryDao;
import com.inn.jewelry.service.CategoryService;
import com.inn.jewelry.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

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

    private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

}
