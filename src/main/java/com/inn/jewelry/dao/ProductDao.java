package com.inn.jewelry.dao;

import com.inn.jewelry.POJO.Product;
import com.inn.jewelry.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
/**

 The ProductDao interface provides database operations for the Product entity.
 */
public interface ProductDao extends JpaRepository<Product,Integer> {

    /**

     Returns a list of ProductWrapper containing all the products in the database.
     @return a list of ProductWrapper
     */
    List<ProductWrapper> getAllProduct();

    /**

     Updates the status of a product with the given id.
     @param status the new status of the product
     @param id the id of the product to be updated
     @return the number of rows affected by the update
     */
    @Modifying
    @Transactional
    Integer updateProductStatus(@Param("status") String status,@Param("id") Integer id);

    /**

     Returns a list of ProductWrapper containing all the products that belong to the category with the given id.
     @param id the id of the category
     @return a list of ProductWrapper
     */
    List<ProductWrapper> getProductByCategory(@Param("id") Integer id);

    /**

     Returns a list of ProductWrapper containing the product with the given id.
     @param id the id of the product
     @return a list of ProductWrapper
     */
    List<ProductWrapper> getProductById(@Param("id") Integer id);
}
