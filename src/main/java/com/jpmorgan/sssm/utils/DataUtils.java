
package com.jpmorgan.sssm.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Utility Class for data purposes
 *
 * @author franciscojosemartinezpaez
 */
public class DataUtils {

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd-HH:mm:ss";
    public static final String EMPTY_STRING = "";

    /**
     * Get System date
     *
     * @param format date format
     * @return date
     */
    public static Date getSysdate(String format) {
        Date sysdate = new Date();
        DateFormat dateFormat = new SimpleDateFormat(format, java.util.Locale.ENGLISH);
        dateFormat.format(sysdate);
        return sysdate;
    }

    /**
     * Verify whether the string given by argument is numeric or not
     *
     * @param str string to be checked
     * @return true whether is numeric
     */
    public static boolean isNumeric(String str) {
        
        boolean res = false;
        
        try {
            Integer.parseInt(str);
            return true;
        } 
        catch (NumberFormatException e) {
            
            str = str.replaceAll(",", ".");

            try {
                Double.parseDouble(str);
                return true;
            }
            catch (NumberFormatException ex) {
            }
        }
        
        return res;
    }

}
