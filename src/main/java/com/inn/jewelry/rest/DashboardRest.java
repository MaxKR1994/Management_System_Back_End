package com.inn.jewelry.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**

 This interface defines REST endpoints for the dashboard of the jewelry store management system.
 */
@RequestMapping(path = "/dashboard")
public interface DashboardRest {

    /**

     The @GetMapping annotation is used to map the getCount() method to an
     HTTP GET request on the /dashboard/details endpoint.
     It specifies that the method should return a ResponseEntity
     containing a Map<String,Object> of entity counts.

     This endpoint returns the count of various entities in the system for the dashboard.
     @return A ResponseEntity containing a Map of entity counts.
     */
    @GetMapping(path = "/details")
    ResponseEntity<Map<String,Object>> getCount();
}
