import java.util.*;
import java.io.*;
import java.lang.Math;

/**
 * Class for finding 'Prime Days' in a year.
 *
 * @author Alex Lake-Smith.
 * COSC326 2021.
 */

public class PrimeDays{

    /**
     * Checks if a number is prime. 
     * @param x: Number to check.
     */
    public static boolean isPrime(int x){        
        if(x <= 1){
            return false;
        }
        
        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) { 
                return false;
            }
        }

        return true;
    }

    
    /**
     * This is where program execution is run.
     * @param args: command line arguments to pass. This would be the file you want to edit.
     */ 
    public static void main(String[] args) {
        int yearLen = 0;
        int[] months = new int[args.length];
        if(args.length > 0){
            for(int i = 0; i < args.length; i++){
                months[i] = Integer.parseInt(args[i]);
            }

            yearLen = months[0];
            for(int i = 1; i < months.length; i++){
                for(int j = 1; j <= months[i]; j++){
                    yearLen++;
                    if(isPrime(j) && isPrime(i+1) && isPrime(yearLen)){
                        System.out.println(yearLen + ": " + (i+1) + " " + j);
                    }                   
                }
            }
        } else{
            System.out.println("Please enter some input to calculate");
        }
    }
}
