package ca.lichangzhang.flooringmastery.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.math.NumberUtils;
import static org.springframework.util.CollectionUtils.isEmpty;

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
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     * Simple method that take in a message to display on the console if there
     * is multiple method, will print all message on one same line
     *
     * @param msg - String of information to display to user.
     */
    @Override
    public void printOneLine(String msg) {
        System.out.print(msg + " ");
    }

    /**
     * Simple method that take in a message to display on the console, and wait
     * for an answer from the user to return
     *
     * @param msg - String explaining what information you want from the
     * @param delimiter - extra text or seperator to seperate the text
     *
     */
    @Override
    public void printOneLine(String msg, String delimiter) {
        System.out.print(msg + " ");
    }

    /**
     * Simple method that takes in a message to display on the console, and
     * continually re-prompt the user with that message until they enter an
     * string which is able to be parsed into date returned as the date to the
     * message
     *
     * @param prompt - String explaining what information you want from the user
     * @param style - the format required for the date to be parsed into and
     * display such as MMddyyyy, ddMMyyyy, or MM/dd/yyyy etc.
     * @return the answer to the message as being able to parsed into date and
     * in proper date format.
     */
    @Override
    public String readDate(String prompt, String style) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(style);
        boolean invalidInput = true;
        String stringValue = null;

        while (invalidInput) {
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                LocalDate.parse(stringValue, formatter); // if it is text, it will break
                invalidInput = false;
            } catch (DateTimeParseException e) {
                // if it explodes, it'll go here and do this
                print("");
                this.print("NOTE:\tInput \"" + stringValue + "\" could not be parsed into date format (" + style + ").  \n\tInput error, please try again.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        }
//        String isoDate = oneDate.toString();
        return stringValue;
    }

    /**
     * Simple method that takes in a message to display on the console, and
     * continually re-prompt the user with that message until they enter an
     * string which is able to be parsed into date returned as the date to the
     * message, and also should be later than min date, following the format
     * style of a style parameter
     *
     * @param prompt - String explaining what information you want from the user
     * @param min - the min date should be. User entry should be later than min
     * date.
     * @param style - the format required for the date to be parsed into and
     * display such as MMddyyyy, ddMMyyyy, or MM/dd/yyyy etc.
     * @return the answer to the message as being able to parsed into date and
     * in proper date format.
     */
    @Override
    public String readDate(String prompt, LocalDate min, String style) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(style);
        String stringValue = null;
        LocalDate dateValue;

        do {
            // print the message prompt to ask user input, should be able to parse into date format
            stringValue = this.readDate(prompt, style);
            dateValue = LocalDate.parse(stringValue, formatter);// if it is text, it will break
            if (dateValue.compareTo(min) < 0) {
                print("");
                print("NOTE:\tThe date entry is earlier than today. \n\tDate should be in the future.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while (dateValue.compareTo(min) < 0);
//        String isoDate = oneDate.toString();
        return stringValue;
    }

    /**
     * Simple method that take in a message to display on the console, and wait
     * for an answer from the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user,
     * @return the answer to the message as String
     */
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return console.nextLine().trim();
    }

    /**
     * Simple method that take in a message to display on the console if there
     * are multiple methods to print text, they will be displayed on one same
     * line, and wait for an answer from the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user,
     * @return the answer to the message as String on one same line
     */
    @Override
    public String readStringOneLine(String prompt) {
        System.out.print(prompt + " ");
        return console.nextLine().trim();
    }

    /**
     * Simple method that take in a message to display on the console and
     * required user to enter string text, it will be mandatory input, not null
     * and not empty and wait for an answer from the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user,
     * @param fieldName - field name of the input field which is required and
     * mandatory
     * @return the answer to the message as String, it will be mandatory field
     */
    @Override
    public String readStringReq(String prompt, String fieldName) {
        String stringValue = null;
        do {
            stringValue = readString(prompt);
            if (stringValue == null || stringValue.trim().length() == 0) {
                print("");
                print("NOTE:\tThis field --- " + fieldName + "--- is mandatory.  \n\tPlease enter some value.");
                print("----------------------------------------------------------------------------------------");
                print("");

            }
        } while (stringValue == null || stringValue.trim().length() == 0);
        return stringValue;
    }

    /**
     * Simple method that take in a message to display on the console and
     * required user to enter string text, it will be mandatory input, not null
     * and not empty and wait for an answer from the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user, must follow the format pattern of [\w,.]
     * @param fieldName - field name of the input field which is required and
     * mandatory
     * @return the answer to the message as String, it will be mandatory field
     */
    @Override
    public String readStringFormat(String prompt, String fieldName) {
        String result;
        do {
            result = readStringReq(prompt, fieldName);
            if (result.matches("[A-Za-z0-9,. ]+") == false) {
                print("");
                print("NOTE:\tThis field --- " + fieldName + "--- can only contain 0-9, a-z, comma and period.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while ((result.matches("[A-Za-z0-9,. ]+") == false));
        return result;
    }
      /**
     * Simple method that take in a message to display on the console and
     * required user to enter string text, it will be mandatory input, not null
     * and not empty and wait for an answer from the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user, must follow the format pattern of [\w,.]
     * @return the answer to the message as String, it will be mandatory field
     */
    @Override
       public String readStringFormatOptional(String prompt) {
        String result;
        do {
            result = readString(prompt);
            if ((result.matches("[A-Za-z0-9,. ]+") == false)&& ( result.trim().length() != 0)) {
                print("");
                print("NOTE:\tThis field can only contain 0-9, a-z, comma and period.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while ((result.matches("[A-Za-z0-9,. ]+") == false) && ( result.trim().length() != 0));
        return result;
    }

    /**
     * Simple method that take in a message to display on the console and
     * required user to enter string text of first and second parameters, it
     * will be mandatory input, not null and not empty and wait for an answer
     * from the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user,
     * @param first - String text of the input field which is required and
     * mandatory
     * @param second - String text of the input field which is required and
     * mandatory
     * @param fieldName - the parameter to be targeted
     * @return the answer to the message as String, it will be mandatory field
     */
    @Override
    public String readStringRestrict(String prompt, String first,
            String second, String fieldName) {
        boolean invalidInput;
        String result = null;
        do {
            result = readStringReq(prompt, fieldName);
            invalidInput = result.equalsIgnoreCase(first)
                    || result.equalsIgnoreCase(second);
            if (invalidInput == false) {
                print("");
                print("******** FLOORING MASTERY ERROR ********");
                print("ERROR:\tPlease type " + first + " or " + second + ".");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while (invalidInput == false);
        return result;
    }

    /**
     * Simple method that take in a message to display on the console and
     * required user to enter numeric string text - from 0 to 9, it allows to
     * begin with 0, it will be mandatory input, not null and not empty and wait
     * for an answer from the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user,
     * @return the answer to the message as String, it will be mandatory field
     */
    @Override
    public String readStringNumeric(String prompt) {
        String result = null;
        do {
            result = readStringReq(prompt, "numeric only");
            if ((NumberUtils.isParsable(result) == false)) {
                print("");
                print("----------------------------------------------------------------------------------------");
                print("NOTE:\tOnly numeric 0 - 9 is allowed.");
            }
        } while ((NumberUtils.isParsable(result) == false));
        return result;
    }
    
      /**
     * Simple method that take in a message to display on the console and
     * required user to enter an string answer which is a value among a List, it will
     * be mandatory input, not null and not empty and wait for an answer from
     * the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user,
     * @param fieldName - String explaining is the name of the field which is mandatory
     * @param listRestricted - List of the input field which is required and
     * mandatory and restricted to the list value, string list
     * @return the answer to the message as String, it will be mandatory field
     */
    @Override
    public String readStringRestrict(String prompt, String fieldName, List<String> listRestricted) {
        boolean invalidInput;
        String result;
        do {
            result = readStringReq(prompt, fieldName );
            invalidInput = listRestricted.contains(result);
            if (invalidInput == false) {
                print("");
                print("******** FLOORING MASTERY ERROR ********");
                print("ERROR: \tThe record for the value you entered ---" + result + "--- does not exist. \n\tThere is no such record.  Please re-enter.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while (listRestricted.contains(result) == false);
        return result;
    }
    
  /**
     * Simple method that take in a message to display on the console and
     * required user to enter a String which is a value among a List, it will
     * be mandatory input, not null and not empty and wait for an answer from
     * the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user,
     * @param listRestricted - List of the input field which is required and
     * mandatory and restricted to the list value, String list
     * @return the answer to the message as Integer, it will be mandatory field
     */
    @Override
    public String readStringRestrict(String prompt, List<String> listRestricted) {
        boolean invalidInput;
        String result;
        do {
            result = readStringReq(prompt, "State Name");
            invalidInput = listRestricted.contains(result);
            if (invalidInput == false) {
                print("");
                print("******** FLOORING MASTERY ERROR ********");
                print("ERROR: \tThe record for the value you entered ---" + result + "--- does not exist. \n\tThere is no such record.  Please re-enter.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while (listRestricted.contains(result) == false);
        return result;
    }
    
    /**
     * Simple method that take in a message to display on the console and
     * required user to enter an integer which is a value among a List, it will
     * be mandatory input, not null and not empty and wait for an answer from
     * the user to return
     *
     * @param prompt - String explaining what information you want from the
     * user,
     * @param listRestricted - List of the input field which is required and
     * mandatory and restricted to the list value, interger list
     * @return the answer to the message as Integer, it will be mandatory field
     */
    @Override
    public int readIntRestrict(String prompt, List<Integer> listRestricted) {
        boolean invalidInput;
        int result;
        do {
            result = readInt(prompt);
            invalidInput = listRestricted.contains(result);         
            if (invalidInput == false) {
                print("");
                print("******** FLOORING MASTERY ERROR ********");
                print("ERROR: \tThe record for the value you entered ---" + result + "--- does not exist. \n\tThere is no such record.  Please re-enter.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }           
        } while ((listRestricted.contains(result) == false) && (isEmpty(listRestricted) == false ));
        return result;
    }

    /**
     * Simple method that takes in a message to display on the console, and
     * continually re-prompt the user with that message until they enter an
     * integer to be returned as the answer to the message
     *
     * @param prompt - String explaining what information you want from the user
     * @return the answer to the message as integer
     */
    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        String stringValue = null;

        while (invalidInput) {
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it is text, it will break
                invalidInput = false;
            } catch (NumberFormatException e) {
                print("");
                // if it explodes, it'll go here and do this
                this.print("NOTE:\tInput \"" + stringValue + "\" is not valid for this question.  \n\tInput error, please try again.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        }
        return num;
    }

    /**
     * Method that takes in a message to display on the console, and continually
     * re-prompt the user with that message until they enter an integer within
     * the specified min/max range to be returned as the answer to that message
     *
     * @param prompt - String explaining what information you want from the user
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an integer value as an answer to the message prompt within the
     * min/max range
     */
    @Override
    public int readInt(String prompt, int min, int max) {
        int result = 0;

        do {
            result = readInt(prompt);
            if (result > max) {
                print("NOTE:\tOut of Range - Invalid Entry.");
                print("\tThe input entry \"" + result + "\" is too high, please enter a lower number between " + min + " and " + max + " (inclusive).");
                print("----------------------------------------------------------------------------------------");
                print("");
            } else if (result < min) {
                print("NOTE:\tOut of Range - Invalid Entry.");
                print("\tThe input entry \"" + result + "\" is too low, please enter a higher number between " + min + " and " + max + " (inclusive).");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while (result < min || result > max);

        return result;
    }

    //take parameter of a int min, input should be more than min, 
    //prompt as another parameter to ask user to input 
    @Override
    public int readInt(String prompt, int min) {
        int result = 0;
        boolean invalidInput = true;
        String stringValue = null;

        while (invalidInput) {
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                result = Integer.parseInt(stringValue); // if it is text, throw exception
                if (result <= min) {
                    throw new IllegalArgumentException();
                }
                invalidInput = false;
            } catch (NumberFormatException e1) {
                this.print("NOTE:\tInput \"" + stringValue + "\" is not number for this question.  Input error, please try again.");
                print("----------------------------------------------------------------------------------------");
                print("");
            } catch (IllegalArgumentException e2) {
                print("NOTE:\tOut of Range - Invalid Entry.");
                print("\tThe input entry \"" + result + "\" is not more than " + min + ", please enter a higher number.");
                print("----------------------------------------------------------------------------------------");
                print("");
            } catch (Exception e3) {
                print("NOTE:\tAn error occurred please try again!");
            }
        }
        return result;
    }

    /**
     * Simple method that takes in a message to display on the console, and
     * continually re-prompt the user with that message until they enter an
     * double to be returned as the answer to the message
     *
     * @param prompt - String explaining what information you want from the user
     * @return the answer to the message as double
     */
    @Override
    public double readDouble(String prompt) {
        boolean invalidInput = true;
        double num = 0;
        String stringValue = null;

        while (invalidInput) {
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                num = Double.parseDouble(stringValue); // if it is text, it will break
                invalidInput = false;
            } catch (NumberFormatException e) {
                // if it explodes, it'll go here and do this
                this.print("NOTE:\tInput \"" + stringValue + "\" is not double. Input error, please try again.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        }
        return num;
    }

    /**
     * Method that takes in a message to display on the console, and continually
     * re-prompt the user with that message until they enter an double within
     * the specified min/max range to be returned as the answer to that message
     *
     * @param prompt - String explaining what information you want from the user
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return a double value as an answer to the message prompt within the
     * min/max range
     */
    @Override
    public double readDouble(String prompt, double min, double max) {
        double result = 0;
        do {
            result = readDouble(prompt);
            if (result > max) {
                print("NOTE:\tOut of Range - Invalid Entry.");
                print("\tThe input entry \"" + result + "\" is too high, please enter a lower number between " + min + " and " + max + " (inclusive).");
                print("----------------------------------------------------------------------------------------");
                print("");
            } else if (result < min) {
                print("NOTE:\tOut of Range - Invalid Entry.");
                print("\tThe input entry \"" + result + "\" is too low, please enter a higher number between " + min + " and " + max + " (inclusive).");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while (result < min || result > max);

        return result;
    }

    /**
     * Simple method that takes in a message to display on the console, and
     * continually re-prompt the user with that message until they enter a float
     * to be returned as the answer to the message
     *
     * @param prompt - String explaining what information you want from the user
     * @return the answer to the message as float
     */
    @Override
    public float readFloat(String prompt) {
        boolean invalidInput = true;
        float num = 0;
        String stringValue = null;

        while (invalidInput) {
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                num = Float.parseFloat(stringValue); // if it is text, it will break
                invalidInput = false;
            } catch (NumberFormatException e) {
                // if it explodes, it'll go here and do this
                this.print("NOTE:\tInput \"" + stringValue + "\" is not float.  Input error, please try again.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        }
        return num;
    }

    /**
     * Method that takes in a message to display on the console, and continually
     * re-prompt the user with that message until they enter a float within the
     * specified min/max range to be returned as the answer to that message
     *
     * @param prompt - String explaining what information you want from the user
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an float value as an answer to the message prompt within the
     * min/max range
     */
    @Override
    public float readFloat(String prompt, float min, float max) {
        float result = 0;
        do {
            result = readFloat(prompt);
            if (result > max) {
                print("NOTE:\tOut of Range - Invalid Entry.");
                print("\tThe input entry \"" + result + "\" is too high, please enter a lower number between " + min + " and " + max + " (inclusive).");
                print("----------------------------------------------------------------------------------------");
                print("");
            } else if (result < min) {
                print("NOTE:\tOut of Range - Invalid Entry.");
                print("\tThe input entry \"" + result + "\" is too low, please enter a higher number between " + min + " and " + max + " (inclusive).");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while (result < min || result > max);

        return result;
    }

    //take in input entry and validate, 
    //take parameter of a BigDecimal min,
    //input should be more than min, 
    //then convert user input into BigDecimal type
    @Override
    public BigDecimal readFloat(String prompt, BigDecimal min) {
        BigDecimal result = BigDecimal.ZERO;
        boolean invalidInput = true;
        String stringValue = null;

        while (invalidInput) {
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                result = new BigDecimal(stringValue).setScale(2, RoundingMode.HALF_UP);// if it is text, throw exception
                if (result.compareTo(min) < 0) {
                    throw new IllegalArgumentException();
                }
                invalidInput = false;
            } catch (NumberFormatException e1) {
                this.print("NOTE:\tInput \"" + stringValue + "\" is not number.  Input error, please try again.");
                print("----------------------------------------------------------------------------------------");
                print("");
            } catch (IllegalArgumentException e2) {
                print("NOTE:\tOut of Range - should be equal to or more than " + min + ".");
                print("\tThe input entry \"" + result + "\" is less than " + min + ", please enter a higher number.");
                print("----------------------------------------------------------------------------------------");
                print("");
            } catch (Exception e3) {
                print("NOTE:\tAn error occurred please try again!");
            }
        }
        return result;
    }

    //take in input entry and validate, 
    //take parameter of a BigDecimal min,
    //input should be more than min, 
    //can be bull, but if there is input entry, need to be more than min
    //then convert user input into BigDecimal type
    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
        BigDecimal result = BigDecimal.ZERO;
        boolean invalidInput = true;
        String stringValue = null;

        while (invalidInput) {
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);

                if (stringValue.length() > 0 && stringValue != null) {
                    // get the input line, and try and parse
                    result = new BigDecimal(stringValue).setScale(2, RoundingMode.HALF_UP);// if it is text, throw exception
                    if (result.compareTo(min) < 0) {
                        throw new IllegalArgumentException();
                    }
                }
                invalidInput = false;
            } catch (NumberFormatException e1) {
                this.print("NOTE:\tInput " + stringValue + " is not number.  Input error, please try again.");
                print("----------------------------------------------------------------------------------------");
                print("");
            } catch (IllegalArgumentException e2) {
                print("NOTE:\tOut of Range - should be equal to or more than " + min + ".");
                print("\tThe input entry " + result + " is less than " + min + ", please enter a higher number.");
                print("----------------------------------------------------------------------------------------");
                print("");
            } catch (Exception e3) {
                print("NOTE:\tAn error occurred please try again!");
            }
        }
        return result;
    }

    /**
     * Simple method that takes in a message to display on the console, and
     * continually re-prompt the user with that message until they enter a long
     * to be returned as the answer to the message
     *
     * @param prompt - String explaining what information you want from the user
     * @return the answer to the message as float
     */
    @Override
    public long readLong(String prompt) {
        boolean invalidInput = true;
        long num = 0;
        String stringValue = null;

        while (invalidInput) {
            try {
                // print the message prompt to ask user input
                stringValue = this.readString(prompt);
                // get the input line, and try and parse
                num = Long.parseLong(stringValue); // if it is text, it will break
                invalidInput = false;
            } catch (NumberFormatException e) {
                // if it explodes, it'll go here and do this
                this.print("NOTE:\tInput \"" + stringValue + "\" is not long. Input error, please try again.");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        }
        return num;
    }

    /**
     * Method that takes in a message to display on the console, and continually
     * re-prompt the user with that message until they enter a long within the
     * specified min/max range to be returned as the answer to that message
     *
     * @param prompt - String explaining what information you want from the user
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an long value as an answer to the message prompt within the
     * min/max range
     */
    @Override
    public long readLong(String prompt, long min, long max) {
        long result = 0;
        do {
            result = readLong(prompt);
            if (result > max) {
                print("NOTE:\tOut of Range - Invalid Entry.");
                print("\tThe input entry \"" + result + "\" is too high, please enter a lower number between " + min + " and " + max + " (inclusive).");
                print("----------------------------------------------------------------------------------------");
                print("");
            } else if (result < min) {
                print("NOTE:\tOut of Range - Invalid Entry.");
                print("\tThe input entry \"" + result + "\" is too low, please enter a higher number between " + min + " and " + max + " (inclusive).");
                print("----------------------------------------------------------------------------------------");
                print("");
            }
        } while (result < min || result > max);

        return result;
    }
}
