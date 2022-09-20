package ca.lichangzhang.assessment;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: May 28, 2022
* 
 */
public class HealthyHearts {

    public static void main(String[] args) {
        
        //declare variable
        String yourAge;
        int intAge;
        int maxRange;
        double targetMin;
        double targetMax;
        
        // scanner class and format class to remove min and max decimal after times by percentage of 0.5 an 0.85
        Scanner inputReader = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#");
        
        // prompt user for input of age, convert string to be integer
        System.out.println("What is your age? ");
        yourAge = inputReader.nextLine();
        intAge = Integer.parseInt(yourAge);
        
        // maximum heart rate range calculation
        maxRange = (220 - intAge);
        
        // target heart rate zone calculation, round up the number
        targetMin = Math.round((maxRange * 0.5));
        targetMax = Math.round(maxRange * 0.85); 
        
        // print out message with correct format of number without decimal
        System.out.println("Your maximum heart rate shoul be " + maxRange + " beats per minutes." );
        System.out.println("Your target HR Zone is "+ df.format(targetMin) + " - " + df.format(targetMax) + " beats per minute.");
      
    }
}
