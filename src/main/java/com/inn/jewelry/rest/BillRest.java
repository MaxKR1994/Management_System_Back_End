package com.inn.jewelry.rest;

import com.inn.jewelry.POJO.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**

 This is an interface named BillRest. It defines the REST endpoints for the bill-related operations.

 @RequestMapping(path = "/bill"): This annotation specifies the base path for all the endpoints in this interface.
 */
@RequestMapping(path = "/bill")
public interface BillRest {

    /**

     This method accepts a request map as input, generates a report based on the input data,
     and returns a response entity with a string message.

     @PostMapping(path = "/generateReport"): This annotation specifies that this method handles
     HTTP POST requests to the"/generateReport" path.
     */
    @PostMapping(path = "/generateReport")
    ResponseEntity<String> generateReport(@RequestBody Map<String,Object> requestMap);

    /**

     This method returns a response entity with a list of all bills.

     @GetMapping(path="/getBills"): This annotation specifies that this method handles
     HTTP GET requests to the "/getBills" path.
     */
    @GetMapping(path="/getBills")
    ResponseEntity<List<Bill>> getBills();

    /**

     This method accepts a request map as input, generates a PDF file based on the input data,
     and returns a response entity with a byte array.

     @PostMapping(path = "/getPdf"): This annotation specifies that this method handles
     HTTP POST requests to the "/getPdf" path.
     */
    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String,Object> requestMap);

    /**

     This method accepts an ID as input, deletes the bill with the given ID,
     and returns a response entity with a string message.

     @DeleteMapping(path = "/delete/{id}"): This annotation specifies that this method handles
     HTTP DELETE requests to the "/delete/{id}" path, where "id" is a path variable.
     */
    @DeleteMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteBill(@PathVariable Integer id);
}
