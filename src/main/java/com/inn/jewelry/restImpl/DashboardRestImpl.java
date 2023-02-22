package com.inn.jewelry.restImpl;

import com.inn.jewelry.rest.DashboardRest;
import com.inn.jewelry.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**

 This class is the implementation of the Dashboard Rest API which provides
 endpoints for getting data for dashboard.
 */
@RestController
public class DashboardRestImpl implements DashboardRest {

    @Autowired
    DashboardService dashboardService;

    /**
     * This method returns the count of different items for the dashboard.
     *
     * @return ResponseEntity<Map<String, Object>> object containing the count of different items.
     */
    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        return dashboardService.getCount();
    }
}
