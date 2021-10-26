import java.util.*;

/**
 * Class for determining if arguments given are a 'Woof'.
 *
 * @author Alex Lake-Smith.
 * COSC326 2021.
 */
public class Woof{

    private static String[] lineArr;
    private static String line;
    private static int woofCount;
    private static String outcome;

    /**
     * Checks if a given input line containers valid characters. 
     * @param s: String to check.
     * @return boolean
     */
    public static boolean lineCheck(String s){
        int count = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 'p' || s.charAt(i) == 'q' || s.charAt(i) == 'r' || s.charAt(i) == 's' || s.charAt(i) == 'N' || s.charAt(i) == 'C' || s.charAt(i) == 'A' || s.charAt(i) == 'K' || s.charAt(i) == 'E'){
                count++;
            }
        }
        if(count == s.length()){
            return true;
        }
        return false;
    }

    /**
     * Checks if a given character is a single woof.
     * @param c: Char to check.
     * @return boolean
     */
    public static boolean isSingle(char c){
        if(c == 'p' || c == 'q' || c == 'r' || c == 's'){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Checks if a given character is a leader.
     * @param c: Char to check.
     * @return boolean
     */
    public static boolean isLead(char c){
        if(c == 'C' || c == 'A' || c == 'K' || c == 'E'){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Checks if a given character is an N.
     * @param c: Char to check.
     * @return boolean
     */
    public static boolean isN(char c){
        if(c == 'N'){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Checks the string to see if it is a woof/not woof.
     * @param s: String to check.
     * @return String: woof or not woof
     */
    public static String woofChecker(String s){
        int expectedWoof = 1;
        int charSearched = 0;
        for(int i = 0; i < line.length(); i++){
            charSearched++;

            //If first char is a woof and string is longer than 1 char.
            if(isSingle(line.charAt(0)) && line.length() > 1){
                outcome = "not woof";
                return outcome;
            }

            //If character is leader
            if(isLead(line.charAt(i))){
                expectedWoof++;
            }

            //If character is N
            if(isN(line.charAt(i))){
                //expectedWoof does not change, therefore no action needed.
            }

            //If character is single
            if(isSingle(line.charAt(i))){
                expectedWoof--;
                if(expectedWoof == 0 && charSearched == line.length()){
                    outcome = "woof";
                } else{
                    outcome = "not woof";
                }
            }
        }
        return outcome;
    }
          
    /**
     * This is where program execution is run.
     * @param args: command line arguments to pass. This would be the file you want to read from.
     */ 
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int index = 0;
        line = "";
        int charSearched = 0;
        while(sc.hasNextLine()){
            line = sc.nextLine();
            lineArr = new String[line.length()];
            lineCheck(line);
            if(lineCheck(line) == true){
                System.out.println(woofChecker(line));
            }
            else{
                System.out.println("not woof");

            }
        }
    }
}
