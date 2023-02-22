package com.inn.jewelry.restImpl;

import com.inn.jewelry.POJO.Bill;
import com.inn.jewelry.constants.StoreConstants;
import com.inn.jewelry.rest.BillRest;
import com.inn.jewelry.service.BillService;
import com.inn.jewelry.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**

 This line defines the implementation class for the BillRest interface.
 The @RestController annotation indicates that this class is a REST controller that will handle incoming HTTP requests.
 */

@RestController
public class BillRestImpl implements BillRest {

    @Autowired
    BillService billService;

    /**

     This method handles an HTTP POST request to generate a report based on the given request parameters.
     The @Override annotation indicates that this method overrides the corresponding method in the BillRest interface.
     The ResponseEntity<String> return type indicates that this method will return an HTTP response with a body of type String.
     */
    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            return billService.generateReport(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**

     This method handles an HTTP GET request to retrieve a list of bills.
     The @Override annotation indicates that this method overrides the corresponding method in the BillRest interface.
     The ResponseEntity<List<Bill>> return type indicates that this method will return an HTTP response with a body of type List<Bill>.
     */
    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try{
            return billService.getBills();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**

     This method handles an HTTP GET request to generate a PDF based on the given request parameters.
     The @Override annotation indicates that this method overrides the corresponding method in the BillRest interface.
     The ResponseEntity<byte[]> return type indicates that this method will return an HTTP response with a body of type byte[].
     */
    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try{
            return billService.getPdf(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**

     This method handles an HTTP POST request to delete a bill with the given ID.
     The @Override annotation indicates that this method overrides the corresponding method in the BillRest interface.
     The ResponseEntity<String> return type indicates that this method will return an HTTP response with a body of type String.
     */
    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try{
            return billService.deleteBill(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
