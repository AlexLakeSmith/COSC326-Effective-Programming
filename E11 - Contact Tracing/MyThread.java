import java.util.*;
public class MyThread extends Thread{

    public static int a;
    public static int t;
    public static int task;
    public static ArrayList<Integer> o = new ArrayList<Integer>();
    public static ArrayList<ArrayList<Integer>> x = new ArrayList<ArrayList<Integer>>();
    public static HashMap<String, Integer> magicPowerMap = new HashMap<String, Integer>();
    public static List<Map<String, Integer>> hashList = new ArrayList<Map<String, Integer>>();
    public static ArrayList<ArrayList<Integer>> currentContacts = new ArrayList<ArrayList<Integer>>();

    /**
     * Class designed to used MultiThreading to find contacts. Works alongside ContactTrace.java.
     *
     * @author Alex Lake-Smith, Mathew Shields.
     * COSC326 2021.
     */

    /**
     * MyThread constructor used to pass parameters through to each one ran.
     * @param a: Find all contacts of this person.
     * @param t: Search from this time.
     * @param x: Which part of the list to search through for contacts.
     * @param o: Store the contacts in this List of Integers.
     * @param task: Correlates to either doing task 1,2 or 3.
     */
    public MyThread(int a, int t, ArrayList<ArrayList<Integer>> x, ArrayList<Integer> o, int task){
        this.a = a;
        this.t = t;
        this.x = x;
        this.o = o;
        this.task = task;
    }

   
    /**
     * Run method which either searches for contacts for a particular person, or works out the indirect contacts for the Magic Power problem.
     */
    @Override
    public void run(){
        /*Tasks 1 or 2, just find contacts from after a certain time and then add them to a list*/
    	if(task == 1 || task == 2){
            ArrayList<Integer> currentContacts = new ArrayList<Integer>();	
            for(int i = 0; i < x.size(); i++){
                ArrayList<Integer> currentList = x.get(i);
                if(currentList.get(0) == a && currentList.get(2) >= t){
                    if(!o.contains(currentList.get(1))){
                        o.add(currentList.get(1));
                    }
                }	
                if(currentList.get(1) == a && currentList.get(2) >= t && currentList.get(0) != a){
                    if(!o.contains(currentList.get(0))){
                        o.add(currentList.get(0));
                    }
                }
            }	
    	}

	/* Magic Power task.*/
    	if(task == 3){
            ArrayList<String> magicGroup = new ArrayList<String>(); 
            magicGroup = ContactTrace.groupListt;
            ArrayList<ArrayList<Integer>> indirectContactCheck = new ArrayList<ArrayList<Integer>>();
            HashMap<String, Integer> indirectContactMap = new HashMap<String, Integer>();
            for(int i = 0; i < x.size(); i++){

                ArrayList<Integer> currentIndirect = x.get(i);

                for(int j = 0; j < ContactTrace.aList.size(); j++){

                    ArrayList<Integer> currentList = ContactTrace.aList.get(j);

		    //Adds the opposite trace. So Trace 0 = Indirect 0, Therefore add opposite trace.
		    if(currentList.get(0) == currentIndirect.get(0) && currentList.get(2) >= currentIndirect.get(1)){
			if(indirectContactMap.containsKey(Integer.toString(currentList.get(1))) == false && magicGroup.contains(Integer.toString(currentList.get(1))) == false){
			    boolean containsK = false;
			    for(int x = 0; x < hashList.size(); x++){
				if(hashList.get(x).containsKey(Integer.toString(currentList.get(1)))){
				    containsK = true;
				}
			    }
			    if(containsK == false){
                                indirectContactMap.put(Integer.toString(currentList.get(1)), 1);
			    }
			    ArrayList<Integer> directContactAdd = new ArrayList<Integer>();
			    directContactAdd.add(currentList.get(1));
			    directContactAdd.add(currentList.get(2));     
			    if(indirectContactCheck.contains(directContactAdd) == false && magicGroup.contains(Integer.toString(directContactAdd.get(0))) == false){
				indirectContactCheck.add(directContactAdd);
			    }
			} else if(indirectContactMap.containsKey(Integer.toString(currentList.get(1))) == true && magicGroup.contains(Integer.toString(currentList.get(1))) == false){
			    boolean containsK = false;
			    for(int x = 0; x < hashList.size(); x++){
				if(hashList.get(x).containsKey(Integer.toString(currentList.get(1)))){
				    containsK = true;
				}
			    }
			    if(containsK == false){
                                int currentChance = indirectContactMap.get(Integer.toString(currentList.get(1)));
                                indirectContactMap.put(Integer.toString(currentList.get(1)), currentChance+1);
			    }

			    ArrayList<Integer> directContactAdd = new ArrayList<Integer>();
			    directContactAdd.add(currentList.get(1));
			    directContactAdd.add(currentList.get(2));
			    if(indirectContactCheck.contains(directContactAdd) == false && magicGroup.contains(Integer.toString(directContactAdd.get(0))) == false){
				indirectContactCheck.add(directContactAdd);
			    }
			}
		    }

		    //Adds the opposite trace. So Trace 1 = Indirect 0, Therefore add opposite trace (0).
		    if(currentList.get(1) == currentIndirect.get(0) && currentList.get(2) >= currentIndirect.get(1)){
			if(indirectContactMap.containsKey(Integer.toString(currentList.get(0))) == false && magicGroup.contains(Integer.toString(currentList.get(0))) == false){
			    boolean containsK = false;
			    for(int x = 0; x < hashList.size(); x++){
				if(hashList.get(x).containsKey(Integer.toString(currentList.get(0)))){
				    containsK = true;
				}
			    }
			    if(containsK == false){
                                indirectContactMap.put(Integer.toString(currentList.get(0)), 1);
			    }
			    ArrayList<Integer> directContactAdd = new ArrayList<Integer>();
			    directContactAdd.add(currentList.get(0));
			    directContactAdd.add(currentList.get(2));
			    if(indirectContactCheck.contains(directContactAdd) == false && magicGroup.contains(Integer.toString(directContactAdd.get(0))) == false){
				indirectContactCheck.add(directContactAdd);
			    }
			} else if(indirectContactMap.containsKey(Integer.toString(currentList.get(0))) == true && magicGroup.contains(Integer.toString(currentList.get(0))) == false){
			    boolean containsK = false;
			    for(int x = 0; x < hashList.size(); x++){
				if(hashList.get(x).containsKey(Integer.toString(currentList.get(1)))){
				    containsK = true;
				}
			    }
			    if(containsK == false){
                                int currentChance = indirectContactMap.get(Integer.toString(currentList.get(0)));
                                indirectContactMap.put(Integer.toString(currentList.get(0)), currentChance+1);                                
			    }
			    ArrayList<Integer> directContactAdd = new ArrayList<Integer>();
			    directContactAdd.add(currentList.get(0));
			    directContactAdd.add(currentList.get(2));
			    if(indirectContactCheck.contains(directContactAdd) == false && magicGroup.contains(Integer.toString(directContactAdd.get(0))) == false){
				indirectContactCheck.add(directContactAdd);
			    }
			}
		    }   
                }
            }
            currentContacts = indirectContactCheck;
            LinkedHashMap<String, Integer> roundMap = new LinkedHashMap<>();
            roundMap.putAll(indirectContactMap);
            hashList.add(roundMap);
            indirectContactMap.clear();
    	}
    }
}
