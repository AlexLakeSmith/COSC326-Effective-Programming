import java.util.*;

/**
 * Class for detecting how sick individuals effect a universe of people.
 *
 * @author Alex Lake-Smith
 */
public class Epidemic{

    /**
     * This is where program execution is run.
     * @param args: command line arguments to pass. This would be the file you want to read from.
     */ 
    public static void main(String[] args){
	Scanner sc = new Scanner(System.in);
	ArrayList<String> universe = new ArrayList<String>();
	Character c = '.';
	while(sc.hasNextLine()){
	    String line = sc.nextLine();
	    if(!line.isEmpty()){
		universe.add(line);
	    } if(line.isEmpty() || !sc.hasNextLine()){
		findSick(universe);
		universe.clear();
	    }
	}
    }


    /**
     * Finds the sick people in the universe for each person, if 2 of that persons direct neighbours are infected  
     * and that person is not immune, they will also become sick. 
     * @param u: ArrayList (universe) to check. 
     */ 
    private static void findSick(ArrayList<String> u) {
	int nonSickCounter = 0;
	char a[][] = new char[u.size()][u.get(0).length()];
	for(int i = 0; i < a.length; i++){
	    a[i] = u.get(i).toCharArray();
	}
    
	// Checks if the universe array is changed for each cell. If it == false. All new sick people have been found.  
	boolean needsChange = true;
	while (needsChange) {
	    needsChange = false;
	    //To go through each row in the universe.
	    for (int row = 0; row < a.length; row++) {
		// To go through each column in the universe. 
		for (int col = 0; col < a[0].length; col++) {
                    if (a[row][col] != '.'){
                        continue;
                    }
                    //nonSickCounter--;
                    int sickCounter = 0;
                    // Checks the top neighbour.
                    if (row > 0 && a[row - 1][col] == 'S'){
                        sickCounter++;
                    } 
                    // Checks the bottom neighbour.
                    if (row < a.length - 1 && a[row + 1][col] == 'S'){
                        sickCounter++;
                    }
                    // Checks the left neighbour.
                    if (col > 0 && a[row][col - 1] == 'S'){
                        sickCounter++;
                    }
                    // Checks the right neighbour. 
                    if (col < a[0].length - 1 && a[row][col + 1] == 'S'){
                        sickCounter++;
                    }
                    // If the sick counter is >= 2. Make this person sick.  
                    if (sickCounter >= 2) {
                        a[row][col] = 'S';
                        needsChange = true;
                    }
                }
            }
        }
	printUniverse(a);
	System.out.println();
    }


    /**
     * Simply print's the universe once it has been transformed.
     * @param a: 2D character array to print
     */ 
    public static void printUniverse(char[][] a){
	for(int row = 0; row < a.length; row++){
	    for(int col = 0; col < a[0].length; col++){
		System.out.print(a[row][col] + "");
	    }
	    System.out.println();
	}
    }



}



  
