import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.*;

/**
 * Class for determining the votes of an election ballet.
 *
 * @author Alex Lake-Smith
 */
public class Election{
    private static HashMap<String, Integer> votes = new HashMap<String, Integer>();
    private static LinkedHashMap<String, Integer> sortedVotes = new LinkedHashMap<>();
    private static ArrayList<ArrayList<String>> aList = new ArrayList<ArrayList<String>>();
    private static List<Map<String, Integer>> hashList = new ArrayList<Map<String, Integer>>();
    private static ArrayList<String> currentLowestCands = new ArrayList<String>();    
    private static String[] splitLine;
    private static String tieToRemove;
    private static int roundNum = 1;
    private static int numCands = 0;
    private static boolean isTie = false;
    
    /**
     * This is where program execution is run.
     * @param args: command line arguments to pass. This would be the file you want to read from.
     */ 
    public static void main(String[] args) {
	String word = "";
	Scanner sc = new Scanner(System.in);
	String line = "";
	int currentRound = 0;

	// Reading in words from file.
	while(sc.hasNextLine()){
            line = sc.nextLine();
            splitLine = new String[line.length()];
            String cleanLine = line.replaceAll("\\s+", " ");
            splitLine = cleanLine.split(" ");
            createVoteLists(splitLine);
        }
        ArrayList<String> temp = new ArrayList<String>();
        for(int i = 0; i < aList.size(); i++){
            ArrayList<String> currentLine = aList.get(i);
            for(int j = 0; j < currentLine.size(); j++){
                if (!temp.contains(currentLine.get(j))){
                    temp.add(currentLine.get(j));
                } 
            }
        }

	int n = temp.size() + 1;

	while(n != 0){
	    isTie = false;
	    countVotes(aList);
	    hashMapSort(votes);

	    if(overHalfCheck(sortedVotes) == true){
		System.exit(0);
	    } 

	    if(unbreakableTieCheck(sortedVotes) == true){
		tieRemover(tieToRemove);
		isTie = true;
		printVotes(sortedVotes);
		votes.clear();
		sortedVotes.clear();
		System.out.println();
	    } else{
		printVotes(sortedVotes);
		roundRemove(aList);
		cleanList(aList);
		System.out.println();
	    }
	    n--;
	}
    }

    /**
     * Counts the votes for the current round. 
     * @param l: List to search through and count.
     */
    public static void countVotes(ArrayList<ArrayList<String>> l){
        int currentVotes = 0;
        String word = "";

        for(int i = 0; i < aList.size(); i++){   
            ArrayList<String> currentLine = aList.get(i);
            if(currentLine.size() == 0){
                continue;
            } 
            word = currentLine.get(0);
            
            if(votes.containsKey(word) == false){
                votes.put(word, 1);
            }else{
                currentVotes = votes.get(word);
                votes.put(word, currentVotes+1);  
            }

            if(currentLine.size() > 1){
                for(int j = 0; j < currentLine.size(); j++){
		    String otherCandidates = currentLine.get(j);
		    if(votes.containsKey(otherCandidates) == false){
			votes.put(otherCandidates, 0);
		    }
		}    
            }
        }
        numCands = votes.size();
    }

    /**
     * Removes the person with the lowest votes for each round. 
     * @param l: List to search through and delete from.
     */
    public static void roundRemove(ArrayList<ArrayList<String>> l){
	String toRemove = "";
	String highestVotes = "";
	int min = Collections.min(sortedVotes.values());
	int max = Collections.max(sortedVotes.values());
	for(String s : sortedVotes.keySet()){
	    if(sortedVotes.get(s) == min){
		toRemove = s;
		sortedVotes.remove(s);
	    } 
	}

	LinkedHashMap<String, Integer> roundXVotes = new LinkedHashMap<>();
	roundXVotes.putAll(sortedVotes);
	hashList.add(roundXVotes);

	//For each ArrayList remove all instances of the lowest voted candidate. 
	for(int i = 0; i < aList.size(); i++){ 
	    ArrayList<String> currentLine = aList.get(i);
	    for(int j = 0; j < currentLine.size(); j++){
		if(currentLine.get(j).equals(toRemove)){
		    currentLine.remove(j);
		}
	    }
	}

	//Clear votes hashmaps. 
	votes.clear();
	sortedVotes.clear();

    }

    /**
     * If a tie, removes the person who has the least amount of votes between those candidates
     * the previous round.
     * @param s:  The candidate to remove.
     */
    public static void tieRemover(String s){
	for(int i = 0; i < aList.size(); i++){ 
	    ArrayList<String> currentLine = aList.get(i);
	    for(int j = 0; j < currentLine.size(); j++){
		if(currentLine.get(j).equals(s)){
		    currentLine.remove(j);
		}
	    }   
	}
    }

    /**
     * Checks the votes for the previous round/rounds if there was a tie. 
     * @param l: List of HM's to search through and count.
     * @return String
     */
    public static String checkPrevRounds(List<Map<String, Integer>> h){
	tieToRemove = "";
	int itCounter = 0;
	int minimum = Collections.max(sortedVotes.values());
	int numLowestCands = 0;
	ArrayList<String> newPool = new ArrayList<String>();    

	for(int i = h.size()-1; i > -1; i--){
            for(String s:h.get(i).keySet()){
                if(currentLowestCands.contains(s)){
                    itCounter++;
                
		    if(minimum > h.get(i).get(s)){
			minimum = h.get(i).get(s);
			newPool.clear();
			newPool.add(s);
		    }

		    else if(minimum == h.get(i).get(s)){
			newPool.add(s);
		    }
		}
	    }

            if(newPool.size() > 1 && i != 0){
                newPool.clear();
            } else if(newPool.size() > 1 && i == 0){
                tieToRemove = "_";
            }
	    else{
                tieToRemove = newPool.get(0);
                return tieToRemove;
	    }   
	}

	return tieToRemove;
    }

    /**
     * Checks if there was an unbreakable tie in that round. 
     * Also checks previous rounds by calling the function.
     * @param v: List to search through.
     * @return boolean
     */
    public static boolean unbreakableTieCheck(Map<String, Integer> v){
	currentLowestCands.clear();
	String winner = "";
	int longestCand = 0;
	int minCount = 0;
	int highest = Collections.max(sortedVotes.values());
	int min = Collections.min(sortedVotes.values());
	for(String s : sortedVotes.keySet()){
	    if(sortedVotes.get(s) == min){
                minCount++;
                currentLowestCands.add(s);
            }

	    if(s.length() > longestCand){
		longestCand = s.length();
            }
	}

	boolean tie = false;        
    
	if(minCount >= 2){
	    tie = true;
	    if(hashList.size() >= 1){
		checkPrevRounds(hashList);
		if(!tieToRemove.equals("_")){
		    return tie;  
		} else{
		    System.out.println("Round " + roundNum);
		    formatVotes(longestCand);
		    System.out.println("Unbreakable tie");
		    System.exit(0);
                }
            } else{
                System.out.println("Round " + roundNum);
                formatVotes(longestCand);
                System.out.println("Unbreakable tie");
                System.exit(0);
            }
        }
	return tie;

    }  

    /**
     * Cleans the list of any empty spaces due to removing the candidates.
     * This is to avoid Null Pointer Exceptions.
     * @param l: List to search through.
     */
    public static void cleanList(ArrayList<ArrayList<String>> l){
	for(int i = 0; i < aList.size(); i++){
	    if(aList.get(i).isEmpty()){
		aList.remove(i);
	    }
	}

    }

    /**
     * Adds each line of the votes for every person who is enrolled to a list.
     * @param line: The line to add to the list.
     */
    public static void createVoteLists(String[] line){
	ArrayList<String> liner  = new ArrayList<String>();
        for(int i = 0; i < splitLine.length; i++){
	    String word = splitLine[i];          
	    if(word.matches("[a-zA-Z]+")){
		liner.add(word);
	    }
	}
	aList.add(liner);
    }

    /**
     * Sorts the HashMap by highest votes to lowest and in alphabetical order.
     * @param v: The map to sort.
     */
    public static void hashMapSort(Map<String, Integer> v){
        Comparator<Entry<String, Integer>> comp = Entry.<String, Integer>comparingByValue().reversed().thenComparing(Entry.comparingByKey());
        sortedVotes = v.entrySet().stream().sorted(comp).collect(Collectors.toMap(Entry::getKey,Entry::getValue,(a, b) -> a, LinkedHashMap::new));
    }

    /**
     * Checks if a candidate got over half the votes for that round, if so they automatically win.
     * @param v: The map to check.
     * @param boolean.
     */
    public static boolean overHalfCheck(Map<String, Integer> v){
        String winner = "";
        int highest = Collections.max(sortedVotes.values());
        double otherVotesCount = 0;
        int longestCand = 0;

        for(String s : sortedVotes.keySet()){
            otherVotesCount += sortedVotes.get(s);
            if(sortedVotes.get(s) == highest){
                winner = s;
            }

            if(s.length() > longestCand){
		longestCand = s.length();
            }

        }

        boolean overHalf = false;
        otherVotesCount = otherVotesCount - sortedVotes.get(winner);
        double total = otherVotesCount + sortedVotes.get(winner);
        double max = Math.round(100.0 / total * sortedVotes.get(winner));
        otherVotesCount = Math.round(100.0 / total * otherVotesCount);
        if(max > 50.0){
            overHalf = true;
            System.out.println("Round " + roundNum);
            formatVotes(longestCand);
            System.out.println("Winner: " + winner);
        }     
        return overHalf;
    }

    /**
     * Prints the results of the votes for the current round.
     * @param v: The map to print.
     */
    public static void printVotes(Map<String, Integer> v){
        String toRemove = "";
        int min = Collections.min(sortedVotes.values());
        int max = Collections.max(sortedVotes.values());
        int longestCand = 0;
        String highestVotes = "";

        System.out.println("Round " + roundNum);
        for(String s : sortedVotes.keySet()){
        

	    if(s.length() > longestCand){
		longestCand = s.length();
            }
        
	    if(sortedVotes.get(s) == min){
		toRemove = s;
            } 

	    if(sortedVotes.get(s) == max)
		highestVotes = s;
        }

        formatVotes(longestCand);

        if(sortedVotes.size() == 2 && currentLowestCands.size() != 2){
            System.out.println("Winner: " + highestVotes);
            System.exit(0);
        } else if(isTie == true){
            System.out.println("Eliminated: " + tieToRemove);
        }else{
            System.out.println("Eliminated: " + toRemove);
        }
        roundNum++;
    }

    /**
     * Formats the votes, based on specification from pdf file. 
     * @param t: Longest candidate String length, to determine correct padding for printing.
     */
    public static void formatVotes(int t){
	int max = Collections.max(sortedVotes.values());
        String highestVotes = "";

        //Formatting. 
        for(String s : sortedVotes.keySet()){
          
	    if(sortedVotes.get(s) == max){
                highestVotes = s;
            } 

	    int n = 100;
	    int stringDif = t - s.length();

	    String spacesRequired = " ";

	    if(s.length() != t){

		StringBuilder b = new StringBuilder(n);
        
		for(int i = 0; i < stringDif; ++i) {
		    b.append(spacesRequired);
		}
        
		for(int i = 0; i < 3; i++){
		    b.append(spacesRequired);
		}
        
		String result = b.toString();
		StringBuilder o = new StringBuilder(n);
		o.append(s + result);
		String output = o.toString();
		System.out.println(output + sortedVotes.get(s));
	    } else{
		StringBuilder b = new StringBuilder(n);
            
		for(int i = 0; i < 3; i++){
		    b.append(spacesRequired);
		}
            
		String result = b.toString();
		StringBuilder o = new StringBuilder(n);
		o.append(s + result);
		String output = o.toString();
		System.out.println(output + sortedVotes.get(s));

            }
        }

    }


}	
