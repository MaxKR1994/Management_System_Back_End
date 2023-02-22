package com.inn.jewelry.serviceImpl;

import com.inn.jewelry.JWT.JwtFilter;
import com.inn.jewelry.POJO.Bill;
import com.inn.jewelry.constants.StoreConstants;
import com.inn.jewelry.dao.BillDao;
import com.inn.jewelry.service.BillService;
import com.inn.jewelry.utils.StoreUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**

 This code is generating a PDF document for a bill for a store. The PDF document includes information about the customer,
 the products purchased, and the total amount of the purchase. The code validates that all the required information is present
 in the request map and inserts the bill into the database.

 The code uses the iText library to create the PDF document. The generateReport method is called when a bill needs to be generated.
 The method takes in a Map object, which contains the required information to generate a bill.
 The method first validates that all the required fields are present in the request map.
 If any of the fields are missing, the method returns a response entity with an error message.

 If all the required fields are present, the method creates a fileName for the bill.
 If the isGenerate flag is not present in the request map or is true, the method inserts the bill into the database.
 The method then creates a PDF document and adds the customer information, product details, and total amount of the purchase
 to the PDF document. Finally, the method saves the PDF document to the file system and returns a response entity
 with the fileName of the generated bill.
 */
@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BillDao billDao;

    /**
     The code uses the iText library to create the PDF document. The generateReport method is called when a bill needs to be generated.
     The method takes in a Map object, which contains the required information to generate a bill.
     The method first validates that all the required fields are present in the request map.
     If any of the fields are missing, the method returns a response entity with an error message.
     If all the required fields are present, the method creates a fileName for the bill.
     If the isGenerate flag is not present in the request map or is true, the method inserts the bill into the database.
     The method then creates a PDF document and adds the customer information, product details, and total amount of the purchase
     to the PDF document. Finally, the method saves the PDF document to the file system and returns a response entity
     with the fileName of the generated bill.
     */
    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("Inside generateReport");
        try{
            String fileName;
            if(validateRequestMap(requestMap)){
                if (requestMap.containsKey("isGenerate") && !(Boolean)requestMap.get("isGenerate")){
                    fileName = (String) requestMap.get("uuid");
                } else {
                    fileName = StoreUtils.getUUID();
                    requestMap.put("uuid",fileName);
                    insertBill(requestMap);
                }
                String data = "Name: " + requestMap.get("name") + "\n"
                            + "Contact Number: " + requestMap.get("contactNumber") + "\n"
                            + "Email: " + requestMap.get("email") + "\n"
                            + "PaymentMethod: " + requestMap.get("paymentMethod");
                Document document = new Document();
                PdfWriter.getInstance(document,new FileOutputStream(StoreConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));
                document.open();
                setRectangleInPdf(document);

                Paragraph chunk = new Paragraph("Store Management System", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data + "\n \n",getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = StoreUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));
                for (int i = 0; i < jsonArray.length(); i++){
                    addRows(table,StoreUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total: "+ requestMap.get("totalAmount") + "\n"
                + "Thank you for visiting. Please visit again!",getFont("Data"));

                document.add(footer);
                document.close();

                return new ResponseEntity<>("{\"uuid\":\"" + fileName +"\"}",HttpStatus.OK);
            }
            return StoreUtils.getResponseEntity(StoreConstants.REQUIRED_DATA_NOT_FOUND,HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("totalAmount");
    }

    /**
     The insertBill method inserts a bill into the database.
     The method creates a Bill object and sets its properties based on the request map.
     The method then saves the Bill object to the database using the BillDao.
     */
    private void insertBill(Map<String, Object> requestMap) {
        try{
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String)requestMap.get("name"));
            bill.setEmail((String)requestMap.get("email"));
            bill.setContactNumber((String)requestMap.get("contactNumber"));
            bill.setPaymentMethod((String)requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String)requestMap.get("totalAmount")));
            bill.setProductDetails((String)requestMap.get("productDetails"));
            bill.setCreatedBy(jwtFilter.getCurrentUser());
            billDao.save(bill);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     The setRectangleInPdf method sets a rectangle in the PDF document.
     The rectangle is used as a border around the PDF document.
     */
    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rect = new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    /**
     The getFont method returns a font object based on the font type.
     The method uses the FontFactory class to create the font object.
     */
    private Font getFont(String type){
        log.info("Inside getFont");
        switch (type){
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
            }
    }

    /**
     The addTableHeader method adds the header row to the product details table in the PDF document.
     The method adds five columns to the table: Name, Category, Quantity, Price, and SubTotal
     */
    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");
        Stream.of("Name", "Category", "Quantity", "Price", "SubTotal")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("addRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double)data.get("price")));
        table.addCell(Double.toString((Double)data.get("total")));
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList<>();
        if (jwtFilter.isAdmin()){
            list = billDao.getAllBills();
        }else {
            list = billDao.getBillByUserName(jwtFilter.getCurrentUser());
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside getPdf : requestMap {}", requestMap);
        try {
            byte[] byteArray = new byte[0];
            if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap)){
                return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);
            }
            String filePath = StoreConstants.STORE_LOCATION + "\\" + (String) requestMap.get("uuid") + ".pdf";
            if (StoreUtils.isFileExist(filePath)){
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }else {
                requestMap.put("isGenerate",false);
                generateReport(requestMap);
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private byte[] getByteArray(String filePath) throws Exception {
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try{
            Optional optional = billDao.findById(id);
            if (!optional.isEmpty()){
                billDao.deleteById(id);
                return StoreUtils.getResponseEntity(StoreConstants.BILL_DELETED_SUCCESSFULLY, HttpStatus.OK);
            }
            return StoreUtils.getResponseEntity(StoreConstants.BILL_ID_DOES_NOT_EXIST, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
