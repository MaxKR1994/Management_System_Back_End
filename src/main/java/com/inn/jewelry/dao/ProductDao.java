package com.inn.jewelry.dao;

import com.inn.jewelry.POJO.Product;
import com.inn.jewelry.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {

    List<ProductWrapper> getAllProduct();

    @Modifying
    @Transactional
    Integer updateProductStatus(@Param("status") String status,@Param("id") Integer id);

    @Modifying
    @Transactional
    List<ProductWrapper> getProductByCategory(@Param("id") Integer id);
}
