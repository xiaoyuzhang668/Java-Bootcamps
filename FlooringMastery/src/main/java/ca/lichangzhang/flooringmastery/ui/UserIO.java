package ca.lichangzhang.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public interface UserIO {
    //print message

    void print(String msg);

    //print message on one same line
    void printOneLine(String msg);

    //print one line message, seperated by delimiter
    public void printOneLine(String msg, String delimiter);

    //readString message to parse string into date following certain format style
    public String readDate(String prompt, String style);

    //readString message to parse string into date and also date should be later than certain date
    //and return value as certain format
    public String readDate(String prompt, LocalDate min, String style);

    //readString message
    String readString(String prompt);

    //readString message
    String readStringOneLine(String prompt);

    //string input mandatory field
    String readStringReq(String prompt, String fieldName);

    String readStringFormat(String prompt, String fileName);

    String readStringFormatOptional(String prompt);

    //retrict user to enter yes or no answer
    String readStringRestrict(String prompt, String first, String second, String fieldName);

    //retrict user to use numeric number only from 0 to 9
    String readStringNumeric(String prompt);

    String readStringRestrict(String prompt, String fieldName, List<String> listRestricted);

    String readStringRestrict(String prompt, List<String> listRestricted);

    int readIntRestrict(String prompt, List<Integer> listRestricted);

    // readInt message and min and max check
    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    int readInt(String prompt, int min);

    //readDouble message and min and max check
    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    // readFloat message and min and max check
    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    BigDecimal readFloat(String prompt, BigDecimal min);// define minimum input for float for BigDecimal
    // readLong message and min and max check

    //read message and return bigdecimal, can be empty but if not empty, need to bigger than 100
    BigDecimal readBigDecimal(String prompt, BigDecimal min);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);
}
