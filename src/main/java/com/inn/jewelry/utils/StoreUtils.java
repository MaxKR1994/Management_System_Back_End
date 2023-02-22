package com.inn.jewelry.utils;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**

 The StoreUtils class provides utility methods for the Store Management System.
 */
@Slf4j
public class StoreUtils {

    /**

     Default constructor for the StoreUtils class.
     */
    public StoreUtils() {
    }

    /**

     Returns an instance of ResponseEntity with the provided message and HttpStatus.
     @param responseMessage the message to be included in the response entity
     @param httpStatus the http status to be included in the response entity
     @return the ResponseEntity instance with the provided message and HttpStatus
     */
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }

    /**

     Returns a UUID string based on the current timestamp.
     @return a UUID string based on the current timestamp
     */
    public static String getUUID(){
        Date data = new Date();
        long time = data.getTime();
        return "BILL - " + time;
    }

    /**

     Returns a JSONArray object from a string representation of a JSON array.
     @param data the string representation of the JSON array
     @return a JSONArray object parsed from the provided string
     @throws JSONException if there is an error parsing the JSON array
     */
    public static JSONArray getJsonArrayFromString(String data) throws JSONException{
        JSONArray jsonArray = new JSONArray(data);
        return jsonArray;
    }

    /**

     Returns a Map object from a JSON string.
     @param data the string representation of a JSON object
     @return a Map object parsed from the provided JSON string
     */
    public static Map<String,Object> getMapFromJson(String data){
        if(!Strings.isNullOrEmpty(data)){
            return new Gson().fromJson(data,new TypeToken<Map<String,Object>>(){}.getType());
        }
        return new HashMap<>();
    }

    /**

     Checks if a file exists at the provided path.
     @param path the path of the file to check for existence
     @return true if the file exists, false otherwise
     */
    public static boolean isFileExist(String path){
        log.info("Inside isFileExist {}", path);
        try {
            File file = new File(path);
            return (file != null && file.exists()) ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

}
