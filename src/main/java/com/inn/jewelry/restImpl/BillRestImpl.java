package com.inn.jewelry.restImpl;

import com.inn.jewelry.constents.StoreConstants;
import com.inn.jewelry.rest.BillRest;
import com.inn.jewelry.service.BillService;
import com.inn.jewelry.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BillRestImpl implements BillRest {

    @Autowired
    BillService billService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            return billService.generateReport(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
