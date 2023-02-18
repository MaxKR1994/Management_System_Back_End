package com.inn.jewelry.rest;

import com.inn.jewelry.POJO.Category;
import com.inn.jewelry.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String,String> requestMap);

    @GetMapping(path="/get")
    ResponseEntity<List<ProductWrapper>> getAllProduct();

}
