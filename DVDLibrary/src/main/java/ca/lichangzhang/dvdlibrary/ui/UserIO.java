package ca.lichangzhang.dvdlibrary.ui;

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
    
    //readString message
    String readString(String prompt);
    
    //readString message
    String readStringOneLine(String prompt);
     
    // readInt message and min and max check
    int readInt(String prompt);
    
    int readInt(String prompt, int min, int max);
    
    //readDouble message and min and max check
    double readDouble(String prompt);
    
    double readDouble(String prompt, double min, double max);
    
    // readFloat message and min and max check
    float readFloat(String prompt);
    
    float readFloat(String prompt, float min, float max);  
    
    // readLong message and min and max check
    long readLong(String prompt);
    
    long readLong(String prompt, long min, long max);   
}
