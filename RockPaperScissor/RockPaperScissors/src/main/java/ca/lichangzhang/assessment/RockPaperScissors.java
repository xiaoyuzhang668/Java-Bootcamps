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
public class RockPaperScissors {

    public static void main(String[] args) {
        
        // declare variable to get input value
        Scanner inputScanner = new Scanner(System.in);
        String playerInput = "";
        
      do {   // program will run at least once, so use do/while loop no matter what is the answer
          // call the playGame method to begin the game
            playGame();
            // will prompt user for yes/no answer when user ends the last round of time
            System.out.println("Do you want to play again?  answer yes or no: "); 
            playerInput = inputScanner.nextLine();
            
            // to check if user answer yes or no, convert to compare on lower case to ignore case sensitive
            if (playerInput.toLowerCase().contains("no")) {
                System.out.println("");
                //if user said no to question, print out message and quit the program
                System.out.println("Thanks for playing!");
                break;
            }
            // can use playInput.equals("yes"), but  will consider lower or capital case entry
            } while (playerInput.toLowerCase().contains("yes")) ;       
        }  
  
        // playGame() method 
        public static void playGame(){
                // declare and initialize variable  
                // variable to keep track of how many rounds played and winning status
                int numRoundDone = 0;

                int timeOfUserWin = 0;
                int timeOfComputerWin = 0 ;
                int timeOfTie = 0;
                
                // input variable
                int choiceInput;
                int computerInput;                

                //random class to generate computer input        
                Random randomizer = new Random();  

                //System.out.println("");
                // call getValue() method
                int numRound = getValue("How many rounds do you want to play? "); 
                System.out.println("");
                
                // the below will run when the answer is in range of 1 - 10
                while ( true ) {          
                    if (numRound > 10 || numRound < 1) {            
                        System.out.println("Error: You entered number " + numRound + ", which is out of desired range 1 - 10.  Quiting the program.");
                        System.exit(0); //quit the program
                    } 
                    
                    // call getValue method to get user input and convert string to number
                    choiceInput = getValue("What is your choice: enter 1 for Rock, 2 for Paper, 3 for Scissors");    
                    // computer generates random number from 1 to 3;
                    computerInput = randomizer.nextInt(3) + 1;
                    System.out.println("Computer: " + computerInput);  

                    // keep track how many round has been played  inside the loop
                    numRoundDone ++; 
                    
                    // player chose 1 = Rock
                    if ( choiceInput == 1) {
                        switch (computerInput) {                
                            case 2:
                                timeOfComputerWin ++;
                                System.out.println("======== Computer won in this round #" + numRoundDone+ " ========\n" );
                                break;
                            case 3: 
                                timeOfUserWin ++;
                                System.out.println("======== User won in this round #" + numRoundDone+ " ========\n" );
                                break;
                            default:                                
                                timeOfTie ++;  
                                System.out.println("======== It's tie in this round #" + numRoundDone + " ========\n");
                        }
                    // player chose 2 = Paper
                    } else if (choiceInput == 2) {
                        switch (computerInput) {                
                            case 1:
                                timeOfUserWin ++;
                                System.out.println("======== User won in this round #" + numRoundDone+ " ========\n" );
                                break;
                            case 3: 
                                timeOfComputerWin ++;
                                System.out.println("======== Computer won in this round #" + numRoundDone+ " ========\n" );
                                break;
                            default:
                                timeOfTie ++; 
                                System.out.println("======== It's tie in this round #" + numRoundDone+ " ========\n" );
                        }  
                    // player chose 3 = Scissors
                    } else if (choiceInput == 3) {
                        switch (computerInput) {                
                            case 1:
                                timeOfComputerWin ++;
                                System.out.println("======== Computer won in this round #" + numRoundDone + " ========\n" );
                                break;
                            case 2: 
                                timeOfUserWin ++;
                                System.out.println("======== User won in this round #" + numRoundDone +" ========\n" );
                                break;
                            default:
                                timeOfTie ++;  
                                System.out.println("======== It's tie in this round #" + numRoundDone + " ========\n");
                       } 
                    } else {  
                        // if user does not enter 1 or 2 or 3, print out error message
                        System.out.println("======== User entered an invalid choice in this round #" + numRoundDone +" ========\n");
                    }                     
                                        
                   // if this is the last round of game, show result message and break out of the loop
                   // to to back to main method
                   if (numRoundDone == numRound) {                    
                        System.out.println("Times of computer win: " + timeOfComputerWin);
                        System.out.println("Times of user win: " + timeOfUserWin);
                        System.out.println("Times of computer and user tie: " + timeOfTie);

                        if (timeOfUserWin > timeOfComputerWin) {
                            System.out.println("The overall winner is User.");
                        } else if (timeOfUserWin < timeOfComputerWin) {
                            System.out.println("The overall winder is Computer.");
                        } else {
                            System.out.println("It is tie between User and Computer.");
                        }
                        System.out.println("");
                        break;            
                       }
                   }         
        }
        
        // getValue() method to get user input, then change string to number
        public static int getValue(String message){
            // declare scanner
            Scanner inputReader = new Scanner(System.in);            
            //display message to prompt user for answer 
            System.out.println(message);
            //wait for user to enter answer
            String userInput = inputReader.nextLine();
            //convert input from string to int
            int intInput = Integer.parseInt(userInput);
            //return value
            return intInput;
    }
}
