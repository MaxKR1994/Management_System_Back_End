package com.inn.jewelry.dao;

import com.inn.jewelry.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**

 This interface serves as the DAO layer for the Category entity, providing methods to access and manipulate Category data.
 */
public interface CategoryDao extends JpaRepository<Category,Integer> {

    /**

     Retrieves all categories from the database.
     @return a list of all Category objects in the database.
     */
    List<Category> getAllCategory();
}
