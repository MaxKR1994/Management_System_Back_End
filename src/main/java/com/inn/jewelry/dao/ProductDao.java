package com.inn.jewelry.dao;

import com.inn.jewelry.POJO.Product;
import com.inn.jewelry.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {

    List<ProductWrapper> getAllProduct();
}
