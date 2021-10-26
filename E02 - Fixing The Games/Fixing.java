import java.util.*;

/**
 * Class for fixing game results of a given table. 
 *
 * @author Alex Lake-Smith.
 * COSC326 2021.
 */

public class Fixing{
    private static List<String[][]> children = new ArrayList<>();
    private static List<String[][]> currentGen = new ArrayList<>();
    private static List<String[][]> orderedArrays = new ArrayList<>();
    private static String[][] games;
    private static String[][] result;
    private static String[] splitLine;
    private static String[] dups;
    private static String line;
    private static int lineLength;
    private static int numDifferentOutcomes = 0;
    private static int currRow = 0;
    private static int currCol = 0;
    private static int p;
    
    /**
     * Main method. This is where program execution is run.
     * Responsible for reading in file data, storing it and then manipulating it 
     * to it's correct format.
     * @param args: command line arguments to pass. This would be the file you want to edit.
     */
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	line = sc.nextLine();
        splitLine = line.split(" ");
	lineLength = splitLine.length;        
        p = splitLine.length;
	games = new String[p][p];
        
        //Reads in the original data into a 2D array. 
        for(int row = 0; row < p; row++){
            for(int col = 0; col < p; col++){
                games[row][col] = splitLine[col]; 
            }
            if(sc.hasNextLine()){
                line = sc.nextLine();
                splitLine = line.split(" ");
            }
            else{
                break;
            }
        }

        // Makes a copy of the original games array and stores it in one called result. 
        result = new String[p][p];
        revertToOrigin(result);

        //Finds duplicates in the first row. Then pushes each of the duplicate entries down. 
        //Adding each child to a list. Reverts the result table to original. 
        findRowDups(games, currRow, currCol);
        for(int col = 0; col < lineLength; col++){
            if(games[currRow][col].equals(dups[0])){
                push(games, currRow, col);
                revertToOrigin(result);
            }
        }
        
        currRow++;

        while(currRow != lineLength-1){
            pushOperation(children);
            generationOverwrite(children);
            currRow++;
        }

        if(currRow == lineLength-1){
            addDash(children, lineLength-1);
            finalTrim(children);
        }

        gamesOrdering(children);
        arrayCompare(orderedArrays);
        printList(children); 
        
        if(numDifferentOutcomes == 0){
            numDifferentOutcomes++;
        }
	System.out.println("Different results: " + numDifferentOutcomes);
        
    }
    
    /**
     * finalTrim method. This is run at the end of the pushes and checks for
     * any duplicate tables and will remove them if found. 
     * @param c: List of 2D arrays to search through.
     */
    private static void finalTrim(List<String[][]> c){  
	for(int i = 0; i < c.size(); i++){
	    if(hasDuplicatesInRows(c.get(i)) == true){
		c.remove(i);
		numDifferentOutcomes = c.size();
		i--;
	    }
	}
    }

    /**
     * hasDuplicatesInRows method. This will check if the rows in a table pushed
     * have any duplicate numbers. 
     * @param inArray: 2D array to search through.
     * @return boolean
     */
    private static boolean hasDuplicatesInRows(String[][] inArray){
	for (int row = 0; row < inArray.length; row++){
	    for (int col = 0; col < inArray[row].length; col++){
		String num = inArray[row][col];
		for (int otherCol = col + 1; otherCol < inArray.length; otherCol++){
		    if (num.equals(inArray[row][otherCol])){
			return true;
		    }
		}
	    }
	}
	return false;
    }

    /**
     * arrayCompare method. Will compare the ordered arrays
     * with each other to check if they are unique. 
     * @param c: List of 2D arrays to search through.
     */
    private static void arrayCompare(List<String[][]> c){
	for(int i = 0; i < c.size(); i++){
	    int j = i+1;
	    while(j < c.size()){
		if(Arrays.deepEquals(c.get(i), c.get(j))){
		    c.remove(i);
		} else{
		    j++;
		}
	    }
	}
    }
            
    /**
     * printArray method. This will simply print a given array.
     * @param t: List of 2D arrays to search through.
     */
    private static void printArray(String[][] t){
        for(int i = 0; i < lineLength; i++){
            for (int j = 0; j < lineLength; j++){
                System.out.print(t[i][j] + " "); 
            }
	    System.out.println();
        } 
    }

    /**
     * gamesOrdering method. This will order the different tables in numberical order 
     * per row. Once all arrays are ordered we can easily check if they are unique.
     * @param c: List of 2D arrays to search through.
     */
    private static void gamesOrdering(List<String[][]> c){
	for(int i = 0; i < c.size(); i++){
	    int position = 0;
	    String[][]ordered = new String[p][p];
	    for(int row = 0; row < p; row++){
                if(c.get(i)[row][position].equals("_")){
                    ordered[position] = c.get(i)[row];
                    ++position;
                    if(position == p){
                        break;
                    }
                    else{
                        row = -1;
                    }
                }
            }
	    orderedArrays.add(ordered);
	}
    }

    /**
     * pushOperation method. This will check if there is a duplicate in the row
     * if true, it will call the pushChilder method.
     * @param c: List of 2D arrays to search through.
     */
    private static void pushOperation(List<String[][]> c){       
        for(int i = 0; i < c.size(); i++){
            findRowDups(c.get(i), currRow, currCol);              
            for(int col = 0; col < lineLength; col++){
                if(c.get(i)[currRow][col].equals(dups[0]) && c.get(i)[lineLength-1][col] == null){
                    pushChildren(c.get(i), currRow, col);
                }
            }
        }
    }

    /**
     * addDash method. This adds a dash to any empty spots in the 2D arrays.
     * This is to stop NullPointerE's.
     * @param c: List of 2D arrays to search through.
     * @param r: Current row number.
     */
    private static void addDash(List<String[][]> c, int r){
	for(int x = 0; x < c.size(); x++){
	    for(int i = 0; i < lineLength; i++){
		if(c.get(x)[r][i] == null){
		    c.get(x)[r][i] = "_";
		}
	    }
	}
    }

    /**
     * generationOverwrite method. This will clear all the children on the previous iteration 
     * and add the ones considered to be the current generation.
     * @param c: List of 2D arrays to search through.
     */
    private static void generationOverwrite(List<String[][]> c){
        children.clear();
        for(int i = 0; i < currentGen.size(); i++){
            children.add(currentGen.get(i));
        }
        currentGen.clear();
    }

    /**
     * pushChildren method. This will push down a number from one of the child branches.
     * It will then add this child push to the currentGen list.
     * @param a: 2D array to edit.
     * @param r: Current row.  
     * @param c: Current col.
     */
    public static void pushChildren(String[][] a, int r, int c){
        String[][] child = new String[lineLength][lineLength]; 
        for(int row = 0; row < lineLength; row++){
            for(int col = 0; col < lineLength; col++){
                child[row][col] = a[row][col];
            }
        }

        for(int row = lineLength-1; row > r; row--){
            child[row][c] = child[row-1][c];
            child[row-1][c] = "_";
        }   
	currentGen.add(child);
    }
    
    /**
     * push method. This will perform the very first push, from then on it will only edit the 
     * children array directly through push children.
     * @param a: 2D array to edit.
     * @param r: Current row.  
     * @param c: Current col.
     */
    public static String[][] push(String[][] a, int r, int c){
        String[][] child = new String[lineLength][lineLength];
        for(int row = lineLength-1; row > r; row--){
            result[row][c] = result[row-1][c];
            result[row-1][c] = "_";
        }

        for(int row = 0; row < lineLength; row++){
            for(int col = 0; col < lineLength; col++){
                child[row][col] = result[row][col];
            }
        }

        children.add(child);
        return result;
    }

    /**
     * findRowDups method. This will simply check which number in the row is a duplicate.
     * @param c: List of 2D arrays to search through.
     * @param a: 2D array to edit.
     * @param r: Current row.  
     * @param c: Current col.
     */
    private static void findRowDups(String[][] a, int r, int c){
	dups = new String[1];
	dups[0] = "";
        int indexed = 0;
        for(int col = 0; col < lineLength; col++){
            int temp = col+1;
            while(temp < lineLength){
                if(a[r][col].equals(a[r][temp++])){
                    dups[0] = a[r][col];              
                }
            }
            temp = col+1;   
        }
    }

    /**
     * revertToOrigin method. Simply reverts the table provided to it's original state.
     * @param a: 2D array to revert.
     * @return 2D String array.
     */
    private static String[][] revertToOrigin(String[][] a){
        for(int row = 0; row < a.length; row++){
            for(int col = 0; col < a.length; col++){
                result[row][col] = games[row][col];
            }
        }
        return result;
    }
    
    /**
     * printList method. This will print all the 2D arrays in a given list. 
     * @param c: List of 2D arrays to print.
     */
    private static void printList(List<String[][]> c){
        for(int i = 0; i < c.size(); i++){
            for(int row = 0; row < lineLength; row++){
                for(int col = 0; col < lineLength; col++){
                    System.out.print(c.get(i)[row][col] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

    }

    //EOF.    
}
