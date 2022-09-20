package ca.lichangzhang.assessment;

/**
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: May 28, 2022
* 
 */
public class SummativeSums {

    public static void main(String[] args) {
        
        // declare and initialize variable 
        int[] arrInput = { 1, 90, -33, -55, 67, -16, 28, -55, 15 };
        int[] arrInput2 = { 999, -60, -77, 14, 160, 301 };
        int[] arrInput3 = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 
        140, 150, 160, 170, 180, 190, 200, -99 }; 
         
        // print out result
        System.out.println("#1 Array Sum: "+ sumUp(arrInput));   
        System.out.println("#2 Array Sum: "+ sumUp(arrInput2));
        System.out.println("#3 Array Sum: "+ sumUp(arrInput3));          
    }
    
        // sumUp() method to add all elements inside array
        public static int sumUp(int[] arrNumber){
            
            // declare and initialize sum total variable which will be the total sum of each element
            int arrSum = 0;

            //loop to add each element to arrSum inside the array
            for ( int i = 0; i < arrNumber.length; i++ ){
                arrSum += arrNumber[i];
            }
            
            //return value 
            return arrSum;
        }
}
