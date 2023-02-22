package com.inn.jewelry.dao;

import com.inn.jewelry.POJO.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**

 The BillDao interface provides methods to perform CRUD operations on Bill entity.
 */
public interface BillDao extends JpaRepository<Bill,Integer> {
    /**

     Returns a list of all bills.
     @return List of all bills
     */
    List<Bill> getAllBills();

    /**

     Returns a list of all bills.
     @return List of all bills
     */
    List<Bill> getBillByUserName(@Param("username") String username);
}
