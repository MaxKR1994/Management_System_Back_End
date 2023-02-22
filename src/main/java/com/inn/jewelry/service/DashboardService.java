package com.inn.jewelry.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**

 Interface for Dashboard service
 */
public interface DashboardService {
    /**

     Method to get count for various entities for dashboard display
     @return ResponseEntity<Map<String, Object>> containing count for entities
     */
    ResponseEntity<Map<String, Object>> getCount();
}
