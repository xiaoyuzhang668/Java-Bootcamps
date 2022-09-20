package ca.lichangzhang.assessment;

import java.util.Random;
import java.util.Scanner;

/**
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: May 28, 2022
* 
 */
public class DogGenetics {

    public static void main(String[] args) {
         // declare variable
         // random integer of each dog breed
        int randomBernad;
        int randomChihua;
        int randomAsian;        
        int randomCur;       
        int randomKing;
        
        Scanner inputReader = new Scanner(System.in);
        Random randomizer = new Random();
           
        String[] dogType = {"St. Bernard", "Chihuahua", "Dramatic RedNosed Asian Pug", "Common Cur", "King Doberman"};
        
        // prompt user for dog's name
        System.out.println("What is your dog's name? ");
        String stringDog = inputReader.nextLine();
               
        System.out.println("Well then, I have this highly "
                + "reliable report on " + stringDog + "'s prestigious background right here.");
        System.out.println("");
        System.out.println(stringDog + " is: ");
        System.out.println("");
        int numTime = 0;
        
        //randomly get random number for each type of dog  
       do {  // random number will be integer from 1 to 100, including 1 and exclusing 100
         randomBernad = randomizer.nextInt(99) + 1;
         randomChihua = randomizer.nextInt(99) + 1;
         randomAsian = randomizer.nextInt(99) + 1;
         randomCur = randomizer.nextInt(99) + 1;
         randomKing = randomizer.nextInt(99) + 1;
            numTime++;
         // if all 5 random number sum up to be 100, break out of loop, otherwise, continue until sum is 100
            if ((randomBernad + randomChihua + randomAsian + randomCur + randomKing) == 100) {
                System.out.println(randomBernad + "% St. Bernard");
                System.out.println(randomChihua + "% Chihuahua");
                System.out.println(randomAsian + "% Dramatic RedNosed Asian Pug");
                System.out.println(randomCur + "% Common Cur");
                System.out.println(randomKing + "% King Doberman"); 
                System.out.println(numTime);
                break;
            }
       } while ((randomBernad + randomChihua + randomAsian + randomCur + randomKing) != 100);
    
        System.out.println("");
        System.out.println("Wow, that's QUITE the dog!  ");
    }
}
