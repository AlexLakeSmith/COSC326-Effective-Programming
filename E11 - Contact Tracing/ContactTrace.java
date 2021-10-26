import java.util.*;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;
import java.util.Map.*;
import java.text.DecimalFormat;

/**
 * Class designed to imintate how contact tracing might be done for a pandemic.
 *
 * @author Alex Lake-Smith, Mathew Shields.
 * COSC326 2021.
 */
public class ContactTrace{
    private static String line;
    private static String[] splitLine;
    public static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList<Integer>> firstCut = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList<Integer>> secondCut = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList<Integer>> thirdCut = new ArrayList<ArrayList<Integer>>();	
    public static ArrayList<ArrayList<Integer>> fourthCut = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList<Integer>> firstHalfIndirect = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList<Integer>> secondHalfIndirect = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<Integer> groupList = new ArrayList<Integer>();
    public static ArrayList<Integer> timesList = new ArrayList<Integer>();
    public static ArrayList<String> groupListt = new ArrayList<String>();
    public static ArrayList<ArrayList<Integer>> contacts = new ArrayList<ArrayList<Integer>>();
    public static HashMap<String, Integer> magicPowerMap = new HashMap<String, Integer>();
    public static ArrayList<ArrayList<Integer>> indirectContactCheck = new ArrayList<ArrayList<Integer>>();
    public static int a;
    public static int t;

    /**
     * This is where program execution is run.
     * @param args: command line arguments to pass. This would be the file you want to read into to create your contacts list.
     */ 
    public static void main(String[] args) throws FileNotFoundException{
        HashSet<Integer> hset = new HashSet<Integer>();
        HashSet<Integer> times = new HashSet<Integer>();
        Scanner sc = new Scanner(new File(args[0]));
        while(sc.hasNextLine()){
            line = sc.nextLine();
            splitLine = new String[line.length()];
            splitLine = line.split(" ");
            listAdder(splitLine);
        }

        for(int xx = 0; xx < aList.size(); xx++){
            ArrayList<Integer> currentList = aList.get(xx);
            hset.add(currentList.get(0));
            hset.add(currentList.get(1));
            times.add(currentList.get(2));
        }
        
        int maxTime = Collections.max(times);
        boolean running = true;
        while(true){
            System.out.println("Enter the task you want to run: 1, 2 or 3.");
            System.out.println("Or type quit to finish running the program.");
            System.out.print("--> ");
            Scanner query = new Scanner(System.in);
            String next = query.next();
            if(next.equals("1")){
                ArrayList<Integer>missingNums = new ArrayList<Integer>();
                boolean isEmpty = true;
                while(isEmpty){
                    System.out.println("\nPlease enter a group of contacts to search for.");
                    Scanner task1 = new Scanner(System.in);

                    int a[]=new int[100];
                    String s[]= task1.nextLine().split(" ");
                    for(int i = 0; i < s.length; i++){
                        if(hset.contains(Integer.parseInt(s[i])) == true){
                            if(groupList.contains(Integer.parseInt(s[i])) == false){
                                groupList.add(Integer.parseInt(s[i]));
                                isEmpty = false;
                            }
                        } else{
                            missingNums.add(Integer.parseInt(s[i]));
                        }
                    }             
                    if(missingNums.isEmpty() == false){
                        System.out.println("\nThese numbers you entered are not in the population: " + missingNums);
                        missingNums.clear();
                        if(isEmpty == false){
                            System.out.println("\nThe search will be done on the valid numbers you entered.");
                            System.out.print("--> ");
                        }
                    }
                }
                System.out.println(groupList);
                boolean timesEmpty = true;
                ArrayList<Integer> missingTimes = new ArrayList<Integer>();
                int iteration = 0;
                while(timesList.size() != groupList.size()){
                    if(iteration == 0){
                        System.out.println("\nEnter " + groupList.size() + " times to search from. One for each corresponding contact.");
                    } else{
                        System.out.println("\nEnter " + (groupList.size()-timesList.size()) + " times to search from. One for each corresponding contact.");
                    }
                    System.out.println("\nPlease enter a time less than or equal to " + maxTime);
                    System.out.print("--> ");
                    Scanner task1p = new Scanner(System.in);
                    int a[]=new int[100];
                    String s[]= task1p.nextLine().split(" ");
                    for(int x = 0; x < s.length; x++){
                        if(times.contains(Integer.parseInt(s[x])) == true){
                            if(timesList.contains(Integer.parseInt(s[x])) == false){
                                timesList.add(Integer.parseInt(s[x]));
                                isEmpty = false;
                            }
                        } else{
                            int invalidTime = Integer.parseInt(s[x]);
                            missingTimes.add(invalidTime);
                            System.out.println(invalidTime +  " is not in the list of valid times. Please enter another time."); 
                        }
                    }
                    iteration++;
                }
                
                System.out.println(timesList);
                for(int i = 0; i < groupList.size(); i++){
                    findContacts(groupList, timesList);          
                }

                System.out.println("Enter p to see all contacts found for each person.");
                System.out.print("--> ");
                Scanner task1pp = new Scanner(System.in);
                String task1print = task1pp.next();
                if(task1print.equals("p")){
                    for(int i = 0; i < groupList.size(); i++){
                        System.out.print("Contacts for person " + groupList.get(i) + " ");
                        System.out.println(contacts.get(i));
                        System.out.println();
                    }
                }
                System.out.println();
            }else if(next.equals("2")){
                ArrayList<Integer>missingNums = new ArrayList<Integer>();
                boolean isEmpty = true;
                while(isEmpty){
                    System.out.println("\nPlease enter a group of contacts to search for.");
                    Scanner task1 = new Scanner(System.in);

                    int a[]=new int[100];
                    String s[]= task1.nextLine().split(" ");
                    for(int i = 0; i < s.length; i++){
                        if(hset.contains(Integer.parseInt(s[i])) == true){
                            if(groupList.contains(Integer.parseInt(s[i])) == false){
                                groupList.add(Integer.parseInt(s[i]));
                                isEmpty = false;
                            }
                        } else{
                            missingNums.add(Integer.parseInt(s[i]));
                        }
                    }             
                    if(missingNums.isEmpty() == false){
                        System.out.println("\nThese numbers you entered are not in the population: " + missingNums);
                        missingNums.clear();
                        if(isEmpty == false){
                            System.out.println("\nThe search will be done on the valid numbers you entered.");
                            System.out.print("--> ");
                        }
                    }
                }
                System.out.println(groupList);
                boolean timesEmpty = true;
                ArrayList<Integer> missingTimes = new ArrayList<Integer>();
                int iteration = 0;
                while(timesList.size() != groupList.size()){
                    if(iteration == 0){
                        System.out.println("\nEnter " + groupList.size() + " times to search from. One for each corresponding contact.");
                    } else{
                        System.out.println("\nEnter " + (groupList.size()-timesList.size()) + " times to search from. One for each corresponding contact.");
                    }
                    System.out.println("\nPlease enter a time less than or equal to " + maxTime);
                    System.out.print("--> ");
                    Scanner task1p = new Scanner(System.in);
                    int a[]=new int[100];
                    String s[]= task1p.nextLine().split(" ");
                    for(int x = 0; x < s.length; x++){
                        if(times.contains(Integer.parseInt(s[x])) == true){   
                            timesList.add(Integer.parseInt(s[x]));
                            isEmpty = false;
                            
                        } else{
                            int invalidTime = Integer.parseInt(s[x]);
                            missingTimes.add(invalidTime);
                            System.out.println(invalidTime +  " is not in the list of valid times. Please enter another time."); 
                        }
                    }
                    iteration++;
                }
                System.out.println();     
		System.out.println("Execution times are below.");
		findContacts(groupList, timesList);
		runThreads(groupList, timesList, 1);
		System.out.println();
                System.out.println("Enter p if you want to see the list of contacts");
                System.out.print("--> ");
                Scanner scan4 = new Scanner(System.in);
                String next4 = scan4.next();
                if(next4.equals("p")){
                    for(int i = 0; i < groupList.size(); i++){
                        System.out.print("Contacts for person " + groupList.get(i) + " ");
                        System.out.println(contacts.get(i));
                        System.out.println();
                    }
                }
                else{
                    groupList.clear();
                    timesList.clear();
                    System.out.println();
                    continue;
                }
                groupList.clear();
                timesList.clear();
            }
            else if(next.equals("3")){
                ArrayList<Integer> missingNums = new ArrayList<Integer>();
                boolean  isEmpty = true;
                while(isEmpty){
                    System.out.println("\nPlease enter a group of people in the population that will catch the magic power. Seperate them by a space.");
                    System.out.print("--> ");
                    Scanner sc3 = new Scanner(System.in);
                    int a[]=new int[100];
                    String s[]= sc3.nextLine().split(" ");
                    for(int i = 0; i < s.length; i++){
                        if(hset.contains(Integer.parseInt(s[i])) == true){
                            if( groupListt.contains(s[i]) == false){
                                groupListt.add(s[i]);
                                isEmpty = false;
                            }
                        } else{
                            missingNums.add(Integer.parseInt(s[i]));
                        }
                    }
                    if(missingNums.isEmpty() == false){
                        System.out.println("\nThese numbers you entered are not in the population: " + missingNums);
                        missingNums.clear();
                        if(isEmpty == false){
                            System.out.println("\nThe power will be given to the valid numbers you entered.");
                            System.out.print("--> ");
                        }
                    }
                }
                System.out.println(groupListt);
                System.out.println("\nEnter the time T to search from. Please enter a time less than or equal to " + maxTime);
                System.out.print("--> ");
                Scanner sc33 = new Scanner(System.in);
                String strT = sc33.next();
                int sc3t = Integer.parseInt(strT);
                while (sc3t > maxTime || t < 0){
                    System.out.println("\nPlease enter a valid time, between 0 and " + maxTime + ".");
                    System.out.print("--> ");
                    sc33 = new Scanner(System.in);
                    sc3t = Integer.parseInt(sc33.next());
                }
                magicPower(groupListt , sc3t);
                groupListt.clear();
                MyThread.hashList.clear();
                MyThread.magicPowerMap.clear();
                MyThread.currentContacts.clear();
            }else if(next.equals("quit")){
                System.exit(0);
            }
        }
    }


    /**
     * This method is used to find all the contacts a person would have had after a particular time.
     * @param al: List of contacts that you want to query.
     * @param ti: List of times to search from for each person.
     */ 
    public static void findContacts(ArrayList<Integer> al,  ArrayList<Integer> ti){
        for(int xx = 0; xx < al.size(); xx++){
            long start = System.currentTimeMillis();
            ArrayList<Integer>currentContacts = new ArrayList<Integer>();
            for(int i = 0; i < aList.size(); i++){
                ArrayList<Integer> currentList = aList.get(i);
                int t = ti.get(xx);
                int a = al.get(xx);

                if(currentList.get(0) == a && currentList.get(2) > t){
                    if(!currentContacts.contains(currentList.get(1))){
                        currentContacts.add(currentList.get(1));
                    }
                }

                if(currentList.get(1) == a && currentList.get(2) > t && currentList.get(0) != a){
                    if(!currentContacts.contains(currentList.get(0))){
                        currentContacts.add(currentList.get(0));
                    }
                } 
            }
            contacts.add(currentContacts);
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("Total execution time for contact " + al.get(xx) + " with time " + ti.get(xx) + " for non thread method: " + timeElapsed + " mills");
        }
        System.out.println();
    }

    /**
     * This function uses Multithreading to either find contacts of a certain group of people 
     * or to calculate the chance's of people in the population contracting the 'Magic Power'. 
     * @param al: List of contacts that you want to query.
     * @param ti: List of times to search from for each person.
     * @param task: Which task to perform. Either search for contacts or search for who gets the magic power.
     */ 
    public static void runThreads(ArrayList<Integer> al, ArrayList<Integer> ti, int task){	
        ArrayList<Integer> th1 = new ArrayList<Integer>();
        ArrayList<Integer> th2 = new ArrayList<Integer>();
        ArrayList<Integer> th3 = new ArrayList<Integer>();
        ArrayList<Integer> th4 = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> mergedContacts = new ArrayList<ArrayList<Integer>>();
        halfList(aList);
        partitionList(aList);
        if(task != 3){      
            /* One threads.*/
            for(int i = 0; i < al.size(); i++){
                MyThread t1 = new MyThread(al.get(i), ti.get(i), aList, th1, 2);
                long start = System.currentTimeMillis();
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                mergedContacts.add(th1);
                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                System.out.println("Total execution time for contact " + al.get(i) + " with time " + ti.get(i) + " for 1 thread method: " + timeElapsed + " mills");
                th1.clear();
                mergedContacts.clear();   
            }    
            System.out.println();
            /* Two threads.*/
            for(int j = 0; j < al.size(); j++){
                MyThread twoThread1 = new MyThread(al.get(j), ti.get(j), firstCut, th1, 2);
                MyThread twoThread2 = new MyThread(al.get(j), ti.get(j), secondCut, th2, 2);
                long start1 = System.currentTimeMillis();
                twoThread1.start();
                twoThread2.start();
                try {
                    twoThread1.join();
                    twoThread2.join();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                mergedContacts.add(th1);
                mergedContacts.add(th2);
                long finish1 = System.currentTimeMillis();
                long timeElapsedT2 = finish1 - start1;
                System.out.println("Total execution time for contact " + al.get(j) + " with time " + ti.get(j) + " for 2 thread method: " + timeElapsedT2 + " mills");
                th1.clear();
                th2.clear();
                mergedContacts.clear();
            } 
            System.out.println();
            /* Four threads.*/
            for(int x = 0; x < al.size(); x++){
                MyThread fourThread1 = new MyThread(al.get(x), ti.get(x), firstCut, th1, 2);
                MyThread fourThread2 = new MyThread(al.get(x), ti.get(x), secondCut, th2, 2);
                MyThread fourThread3 = new MyThread(al.get(x), ti.get(x), thirdCut, th3, 2);
                MyThread fourThread4 = new MyThread(al.get(x), ti.get(x), fourthCut, th4, 2);
                long start2 = System.currentTimeMillis();
                fourThread1.start();
                fourThread2.start();
                fourThread3.start();
                fourThread4.start();
                try {
                    fourThread1.join();
                    fourThread2.join();
                    fourThread3.join();
                    fourThread4.join();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                long finish2 = System.currentTimeMillis();
                long timeElapsedT4 = finish2 - start2;

                long startMerge = System.currentTimeMillis();
                mergedContacts.add(th1);
                mergedContacts.add(th2);
                mergedContacts.add(th3);
                mergedContacts.add(th4);
                long finishMerge = System.currentTimeMillis();
                long timeElapsedMerge = startMerge - finishMerge;
                System.out.println("Total execution time for contact " + al.get(x) + " with time " + ti.get(x) + " for 4 thread method: " + timeElapsedT4 + " mills");
                th1.clear();
                th2.clear();
                th3.clear();
                th4.clear();
                mergedContacts.clear();
            }
            System.out.println();
            firstCut.clear();
            secondCut.clear();
            thirdCut.clear();
            fourthCut.clear();
        }
        /**Finding contacts who might catch the magic power.*/ 
        else if(task == 3){
            int i = 0;
            if(aList.size() < 3000){
                for(int j = 0; j < 6; j++){
                    if(i == 0){
                        i++;    
                        MyThread t1 = new MyThread(a, t, indirectContactCheck, th1, 3);
                        t1.start();
                        try{
                            t1.join();
                        } catch(InterruptedException e){
                            System.out.println(e);
                        }
                    } else{
                        i++;
                        MyThread t1 = new MyThread(a, t, MyThread.currentContacts, th1, 3);
                        t1.start();
                        try {
                            t1.join();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                } 
            } else{
                for(int j = 0; j > 5; j++){
                    if(i == 0){
                        i++;    
                        MyThread t1 = new MyThread(a, t, indirectContactCheck, th1, 3);
                        t1.start();
                        try{
                            t1.join();
                        } catch(InterruptedException e){
                            System.out.println(e);
                        }
                    } else{
                        i++;
                        MyThread t1 = new MyThread(a, t, MyThread.currentContacts, th1, 3);
                        t1.start();
                        try {
                            t1.join();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                }
            }

        }
    }

    /**
     * Cleans the HashMap list of any current contacts.
     */
    public static void cleanList(){
        for(int i = 0; i < MyThread.hashList.size(); i++){
            if(MyThread.hashList.get(i).isEmpty()){
                MyThread.hashList.remove(i);
                i--;
            }
        }
    }
    
    /**
     * Prints the chances of someone in that population catching the magic power.
     */   
    public static void printChances(){
        int depth =(MyThread.hashList.size() + 1);
        System.out.println("Chance of each individual getting the power:\n");
        if(aList.size() < 3000){
            System.out.println("Program has gone 6 levels deep." );
 
        } else if(aList.size() > 3000){
            System.out.println("Program has gone 4 levels deep." );
        }
        
        double chance = 0.4;
        int maxLength = 0;
        HashMap<String, Double> finalScores = new HashMap<String, Double>();
        for(String s: magicPowerMap.keySet()){            
            double directChance = magicPowerMap.get(s) * chance;;
            finalScores.put(s, directChance);

            if (s.length() > maxLength) {
                maxLength = s.length();
            }
        }       
        for(int i = 0; i < MyThread.hashList.size(); i++){
            for(String ss: MyThread.hashList.get(i).keySet()){               
                double currentScore = 0;
                if (ss.length() > maxLength) {
                    maxLength = ss.length();
                }
                if (finalScores.containsKey(ss) == true) {
                    currentScore = finalScores.get(ss);
                    if (i % 2 == 0) {
                        currentScore +=  MyThread.hashList.get(i).get(ss) * Math.pow(chance, (0.5 * i + 2));
                    } else {
                        currentScore +=  MyThread.hashList.get(i).get(ss) * Math.pow(chance, (0.5 * (i - 1) + 2));
                    }
                    finalScores.put(ss, currentScore);
                } else {
                    if (i % 2 == 0) {    
                        finalScores.put(ss, MyThread.hashList.get(i).get(ss) * Math.pow(chance, (0.5 * i + 2)));                                       
                    } else {
                        finalScores.put(ss, MyThread.hashList.get(i).get(ss) * Math.pow(chance, (0.5 * (i - 1) + 2)));               
                    }
                }               
            }
        }

        LinkedHashMap<String, Double> reverseSortedMap = new LinkedHashMap<>();
        finalScores.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue())); 
        int padding = 0;
        
        for (String s : reverseSortedMap.keySet()) {     
            double percentage = (Math.round(reverseSortedMap.get(s) * 100.0) / 100.0) * 100.0;
            padding = 2 + maxLength - s.length();
            if(percentage < 100.0){
                if(s.length() == 2){
                    System.out.println(s + "     :    " + percentage +"%");
                } else if(s.length() == 1){
                    System.out.println(s + "       :    " + percentage +"%");
                } else if(s.length() == 3){
                    System.out.println(s + "   :    " + percentage +"%");
                } 
            } else{
                System.out.println(s + "      :    95%");
            }
        }
        System.out.println("\n");
        reverseSortedMap.clear();
        finalScores.clear();
    }

    /**
     * Adds each trace to a list which is then added to an List of ArrayLists.
     * @param line: Line to be read in from a scanner (called in main method)
     */
    public static void listAdder(String[] line){
        ArrayList<Integer> newLine = new ArrayList<Integer>();
        for(int i = 0; i < line.length; i++){
            newLine.add(Integer.parseInt(line[i]));
        }
        aList.add(newLine);
    }

    /**
     * Partitions the list of traces into 4.
     * @param a: The list to partition
     */
    public static void partitionList(ArrayList<ArrayList<Integer>> a){
        int partitionSize = (aList.size()-1)/4;
        for (int i = 0; i < aList.size(); i++){
            if (i < partitionSize) {
                firstCut.add(aList.get(i));
            } else if(i < partitionSize*2){
                secondCut.add(aList.get(i));
            } else if(i < partitionSize * 3){
                thirdCut.add(aList.get(i));
            }
            else {
                fourthCut.add(aList.get(i));
            }
        }
    }

    /**
     * Splits the list into half.
     * @param a: The list to split.
     */
    public static void halfList(ArrayList<ArrayList<Integer>> a){
        for (int i = 0; i < aList.size(); i++){
            if (i < (aList.size() + 1)/2) {
                firstCut.add(aList.get(i));
            }
            else {
                secondCut.add(aList.get(i));
            }
        }
    }

    /**
     * Halve's the list of indirect contacts to use in Multithreading.
     * This is used for the magic power task.
     * @param a: The list to split.
     */
    public static void halfListIndirect(ArrayList<ArrayList<Integer>> a){
        for (int i = 0; i < a.size(); i++){
            if (i < (a.size() + 1)/2) {
                firstHalfIndirect.add(a.get(i));
            }
            else {
                secondHalfIndirect.add(a.get(i));
            }
        }
    }

    /**
     * This method finds all people who have contacted someone with the magic power. It will create a list of indirect contacts.
     * These indirect contacts will then be put through threads to find who they might have contacted etc. This goes up to 6 levels deep.
     * @param l: List of people who have the magic power.
     * @param t: The time to search from for contacts.
     */
    public static void magicPower(List<String> l, int t){
        //For direct contact. 
        for(int i = 0; i < l.size(); i++){
            String currentPerson = l.get(i);
            int intVers = Integer.parseInt(currentPerson);
            for(int j = 0; j < aList.size(); j++){
                ArrayList<Integer> currentList = aList.get(j);
		
                if(currentList.get(0) == intVers && currentList.get(1) != intVers && currentList.get(2) >= t && l.contains(Integer.toString(currentList.get(1))) == false){
                    if(magicPowerMap.containsKey(Integer.toString(currentList.get(1))) == false){
                        magicPowerMap.put(Integer.toString(currentList.get(1)), 1);
                        ArrayList<Integer> directContactAdd = new ArrayList<Integer>();
                        directContactAdd.add(currentList.get(1));
                        directContactAdd.add(currentList.get(2));
                        indirectContactCheck.add(directContactAdd);
                    } else if(magicPowerMap.containsKey(Integer.toString(currentList.get(1))) == true){
                        int currentChance = magicPowerMap.get(Integer.toString(currentList.get(1)));
                        magicPowerMap.put(Integer.toString(currentList.get(1)), currentChance+1);
                        ArrayList<Integer> directContactAdd = new ArrayList<Integer>();
                        directContactAdd.add(currentList.get(1));
                        directContactAdd.add(currentList.get(2));
                        indirectContactCheck.add(directContactAdd);
                    }
                }	

                if(currentList.get(1) == intVers && currentList.get(0) != intVers && currentList.get(2) >= t && l.contains(Integer.toString(currentList.get(0))) == false){
                    if(magicPowerMap.containsKey(Integer.toString(currentList.get(0))) == false){
                        magicPowerMap.put(Integer.toString(currentList.get(0)), 1);
                        ArrayList<Integer> directContactAdd = new ArrayList<Integer>();
                        directContactAdd.add(currentList.get(0));
                        directContactAdd.add(currentList.get(2));
                        indirectContactCheck.add(directContactAdd);
                    } else if(magicPowerMap.containsKey(Integer.toString(currentList.get(0))) == true){
                        int currentChance = magicPowerMap.get(Integer.toString(currentList.get(0)));
                        magicPowerMap.put(Integer.toString(currentList.get(0)), currentChance+1);
                        ArrayList<Integer> directContactAdd = new ArrayList<Integer>();
                        directContactAdd.add(currentList.get(0));
                        directContactAdd.add(currentList.get(2));
                        indirectContactCheck.add(directContactAdd);
                    }
                }

            }
        }

        runThreads(groupList, timesList, 3);
        cleanList();
        printChances();
        magicPowerMap.clear();
        indirectContactCheck.clear();
        indirectContactCheck.clear();
    } 
}
