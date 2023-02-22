package com.inn.jewelry.serviceImpl;

import com.inn.jewelry.dao.BillDao;
import com.inn.jewelry.dao.CategoryDao;
import com.inn.jewelry.dao.ProductDao;
import com.inn.jewelry.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**

 The DashboardServiceImpl class provides implementation for DashboardService interface.
 It is responsible for retrieving counts of categories, products and bills for displaying on dashboard.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    BillDao billDao;

    /**
     * This method retrieves counts of categories, products and bills from corresponding DAO classes
     * and returns them in a map.
     * @return A map containing counts of categories, products and bills.
     */
    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryDao.count());
        map.put("product", productDao.count());
        map.put("bill", billDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
