package com.inn.jewelry.service;

import com.inn.jewelry.POJO.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**

 The BillService interface provides methods to manage bills in the system.
 */
public interface BillService {

    /**

     Generates a report based on the given requestMap.
     @param requestMap A map containing the parameters required to generate the report.
     @return A ResponseEntity containing a message indicating whether the report was generated successfully or not,
     along with an appropriate HTTP status code.
     */
    ResponseEntity<String> generateReport(Map<String, Object> requestMap);

    /**

     Retrieves a list of all bills in the system.
     @return A ResponseEntity containing a list of Bill objects, along with an appropriate HTTP status code.
     */
    ResponseEntity<List<Bill>> getBills();

    /**

     Retrieves a PDF of the bill based on the given requestMap.
     @param requestMap A map containing the parameters required to generate the PDF.
     @return A ResponseEntity containing the PDF as a byte array, along with an appropriate HTTP status code.
     */
    ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap);

    /**

     Deletes the bill with the specified ID.
     @param id The ID of the bill to be deleted.
     @return A ResponseEntity containing a message indicating whether the bill was deleted successfully or not,
     along with an appropriate HTTP status code.
     */
    ResponseEntity<String> deleteBill(Integer id);
}
