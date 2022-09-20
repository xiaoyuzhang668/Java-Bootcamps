package ca.lichangzhang.dvdlibrary.ui;

import java.util.Scanner;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
*/
public class UserIOImpl implements UserIO {

    final private Scanner console = new Scanner(System.in);
    
    /**
    * Simple method that take in a message to display on the console
    * 
    * @param msg - String of information to display to user.
    */
    @Override
    public void print(String msg){
        System.out.println(msg);
    }
    
    /**
    * Simple method that take in a message to display on the console 
    * if there is multiple method, will print all message on one same line
    * 
    * @param msg - String of information to display to user.
    */
    @Override
    public void printOneLine(String msg) {
        System.out.print(msg + " ");
    }
    
    /**
     * Simple method that take in a message to display on the console,
     * and wait for an answer from the user to return
     * 
     * @param prompt - String explaining what information you want from the suer,
     * @return the answer to the message as String
     */
    @Override
    public String readString(String prompt){
        System.out.println(prompt);
        return console.nextLine().trim();
    }
    
    /**
     * Simple method that take in a message to display on the console 
     * if there are multiple methods to print text, they will be displayed on one same line,
     * and wait for an answer from the user to return
     * 
     * @param prompt - String explaining what information you want from the suer,
     * @return the answer to the message as String on one same line
     */
    @Override
    public String readStringOneLine(String prompt) {
        System.out.print(prompt + " ");
        return console.nextLine().trim();
    }
     
   /**
    * Simple method that takes in a message to display on the console,
    * and continually re-prompt the user with that message until they enter an integer
    * to be returned as the answer to the message
    * 
    * @param prompt - String explaining what information you want from the user
    * @return the answer to the message as integer
    */
    @Override
    public int readInt(String prompt){
        boolean invalidInput = true;
        int num = 0;
        String stringValue = null;
        
        while (invalidInput){
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it is text, it will break
                invalidInput = false;
            } catch (NumberFormatException e) {
                // if it explodes, it'll go here and do this
                this.print("Input \"" + stringValue + "\" is not number.  Input error, please try again.");
                print("============================================================");
                print("");
            }
        }
        return num;
    }
    
    /**
     * Method that takes in a message to display on the console,
     * and continually re-prompt the user with that message until they enter an integer
     * within the specified min/max range to be returned as the answer to that message
     * 
     * @param prompt - String explaining what information you want from the user
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an integer value as an answer to the message prompt within the min/max range
    */
    @Override
    public int readInt(String prompt, int min, int max){
        int result = 0;

        do {
            result = readInt(prompt);
            if (result > max){
                print("Out of Range - Invalid Entry.");
                print("The input entry \"" + result + "\" is too high, please enter a lower number between "  + min + " and " + max + " (inclusive).");
                print("============================================================");
                print("");
            } else if (result < min ){
                 print("Out of Range - Invalid Entry.");
                 print("The input entry \"" + result + "\" is too low, please enter a higher number between "  + min + " and " + max + " (inclusive).");;;
                 print("============================================================");
                 print("");
            }      
        } while (result < min || result > max);
        
        return result;
    }
    
    /**
    * Simple method that takes in a message to display on the console,
    * and continually re-prompt the user with that message until they enter an double
    * to be returned as the answer to the message
    * 
    * @param prompt - String explaining what information you want from the user
    * @return the answer to the message as double
    */
    @Override
    public double readDouble(String prompt){
        boolean invalidInput = true;
        double num = 0;
        String stringValue = null;
        
        while (invalidInput){
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                num = Double.parseDouble(stringValue); // if it is text, it will break
                invalidInput = false;
            } catch (NumberFormatException e) {
                // if it explodes, it'll go here and do this
                this.print("Input \"" + stringValue + "\" is not double. Input error, please try again.");
                print("============================================================");
                print("");
            }
        }
        return num;
    }
    
    /**
     * Method that takes in a message to display on the console,
     * and continually re-prompt the user with that message until they enter an double
     * within the specified min/max range to be returned as the answer to that message
     * 
     * @param prompt - String explaining what information you want from the user
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return a double value as an answer to the message prompt within the min/max range
    */
    @Override
    public double readDouble(String prompt, double min, double max){
        double result = 0;
        do {
            result = readDouble(prompt);
            if (result > max){
                print("Out of Range - Invalid Entry.");
                print("The input entry \"" + result + "\" is too high, please enter a lower number between "  + min + " and " + max + " (inclusive).");
                print("============================================================");
                print("");
            } else if (result < min ){
                 print("Out of Range - Invalid Entry.");
                 print("The input entry \"" + result + "\" is too low, please enter a higher number between "  + min + " and " + max + " (inclusive).");;;
                 print("============================================================");
                 print("");
            } 
        } while (result < min || result > max);
        
        return result;
    }
    
     /**
    * Simple method that takes in a message to display on the console,
    * and continually re-prompt the user with that message until they enter a float
    * to be returned as the answer to the message
    * 
    * @param prompt - String explaining what information you want from the user
    * @return the answer to the message as float
    */
    @Override
    public float readFloat(String prompt){
        boolean invalidInput = true;
        float num = 0;
        String stringValue = null;
        
        while (invalidInput){
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                num = Float.parseFloat(stringValue); // if it is text, it will break
                invalidInput = false;
            } catch (NumberFormatException e) {
                // if it explodes, it'll go here and do this
                this.print("Input \"" + stringValue + "\" is not float.  Input error, please try again.");
                print("============================================================");
                print("");
            }
        }
        return num;
    }
    
    /**
     * Method that takes in a message to display on the console,
     * and continually re-prompt the user with that message until they enter a float
     * within the specified min/max range to be returned as the answer to that message
     * 
     * @param prompt - String explaining what information you want from the user
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an float value as an answer to the message prompt within the min/max range
    */
    @Override
    public float readFloat(String prompt, float min, float max){
        float result = 0;
        do {
            result = readFloat(prompt);
             if (result > max){
                print("Out of Range - Invalid Entry.");
                print("The input entry \"" + result + "\" is too high, please enter a lower number between "  + min + " and " + max + " (inclusive).");
                print("============================================================");
                print("");
            } else if (result < min ){
                print("Out of Range - Invalid Entry.");
                print("The input entry \"" + result + "\" is too low, please enter a higher number between "  + min + " and " + max + " (inclusive).");;;
                print("============================================================");
                print("");
            } 
        } while (result < min || result > max);
        
        return result;
    }
    
    /**
    * Simple method that takes in a message to display on the console,
    * and continually re-prompt the user with that message until they enter a long
    * to be returned as the answer to the message
    * 
    * @param prompt - String explaining what information you want from the user
    * @return the answer to the message as float
    */
    @Override
    public long readLong(String prompt){
        boolean invalidInput = true;
        long num = 0;
        String stringValue = null;
        
        while (invalidInput){
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                num = Long.parseLong(stringValue); // if it is text, it will break
                invalidInput = false;
            } catch (NumberFormatException e) {
                // if it explodes, it'll go here and do this
                this.print("Input \"" + stringValue + "\" is not long. Input error, please try again.");
                print("============================================================");
                print("");
            }
        }
        return num;
    }
    
    /**
     * Method that takes in a message to display on the console,
     * and continually re-prompt the user with that message until they enter a long
     * within the specified min/max range to be returned as the answer to that message
     * 
     * @param prompt - String explaining what information you want from the user
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an long value as an answer to the message prompt within the min/max range
    */
    @Override
    public long readLong(String prompt, long min, long max){
        long result = 0;
        do {
            result = readLong(prompt);
            if (result > max){
                print("Out of Range - Invalid Entry.");
                print("The input entry \"" + result + "\" is too high, please enter a lower number between "  + min + " and " + max + " (inclusive).");
                print("============================================================");
                print("");
            } else if (result < min ){
                print("Out of Range - Invalid Entry.");
                print("The input entry \"" + result + "\" is too low, please enter a higher number between "  + min + " and " + max + " (inclusive).");;;
                print("============================================================");
                print("");
            } 
        } while (result < min || result > max);
        
        return result;
    } 
}
