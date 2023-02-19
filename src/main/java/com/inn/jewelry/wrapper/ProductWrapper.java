package com.inn.jewelry.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWrapper {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String status;
    private Integer categoryId;
    private String categoryName;

    public ProductWrapper(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
