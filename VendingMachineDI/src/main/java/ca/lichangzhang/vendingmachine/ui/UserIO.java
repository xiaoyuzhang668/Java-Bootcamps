package ca.lichangzhang.vendingmachine.ui;

import java.math.BigDecimal;

/**
 *
 * @author catzh Name: Li Chang Zhang Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 */
public interface UserIO {

    //print message
    void print(String msg);

    //print message on one same line
    void printOneLine(String msg);

    //print one line message, seperated by delimiter
    public void printOneLine(String msg, String delimiter);

    //readString message
    String readString(String prompt);

    //readString message
    String readStringOneLine(String prompt);

    //string input mandatory field
    String readStringReq(String prompt, String fieldName);

    //retrict user to enter yes or no answer
    String readStringRestrict(String prompt, String first, String second, String fieldName);

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

    BigDecimal readFloat(String prompt, float min); // define minimum input for float for BigDecimal

    BigDecimal readBigDecimal(String prompt, BigDecimal min); // define minimum input for bigdecimal entry for BigDecimal

    // readLong message and min and max check
    long readLong(String prompt);

    long readLong(String prompt, long min, long max);
}
