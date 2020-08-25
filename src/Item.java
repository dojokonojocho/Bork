/**
 * This class will hold items that are in different rooms throughout
 * bork and the items can be used to increasing health and taking actions
 * throughout the game.
 * @author (Green Group Bork)
 * @see .bork file
 * @version (April 2, 2018)
 */
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.regex.Pattern;
public class Item {
    private String primaryName;
    private int weight;
    private String combatType;
    private int damage;
    private int stamina;
    private boolean primary;
    private Hashtable<String, String> messages = new Hashtable<String, String>();
    private ArrayList<String> transform = new ArrayList<String>();
    private ArrayList<String> dissapear = new ArrayList<String>();
    private ArrayList<String> wound = new ArrayList<String>();
    private ArrayList<String> score = new ArrayList<String>();
    private ArrayList<String> win = new ArrayList<String>();
    private ArrayList<String> die = new ArrayList<String>();
    private ArrayList<String> teleport = new ArrayList<String>();
    
    
    public Item(Scanner s) {
        primaryName = s.nextLine();
        if (!primaryName.equals("===")) {
            // weight = Integer.valueOf(s.nextLine());
            boolean swtch = true;
            String line = "";
            s.nextLine();
            while(swtch){
                line = s.nextLine();
                if (line.startsWith("Type")) {
                	String[] combatArray = line.split(":");
                	combatType = combatArray[1];
                	if (combatType.equals("melee")) {
                		String[] primArray = s.nextLine().split(":");
                		primary = Boolean.parseBoolean(primArray[1]);
                		String[] damageArray = s.nextLine().split(":");
                		damage = Integer.valueOf(damageArray[1]);
                		String[] stamArray = s.nextLine().split(":");
                		stamina = Integer.valueOf(stamArray[1]);
                	}
                	line = s.nextLine();
                }
                if (!line.equals("---")) {
                    String[] message = line.split(":");
                    messages.put(message[0], message[1]);
                    if(message[0].contains("[") == true){
                        String[] verb = message[0].split(Pattern.quote("["));
                        if(message[0].contains("Wound")){
                            wound.add(message[0]);
                        }
                        if(message[0].contains("Transform")){ 
                            transform.add(message[0]);
                        }
                        if(message[0].contains("Disappear")){ 
                            dissapear.add(message[0]);
                        }
                        if(message[0].contains("Score")){ 
                            score.add(message[0]);
                        }
                        if(message[0].contains("Die")){ 
                            die.add(message[0]);
                        }
                        if(message[0].contains("Win")){ 
                            win.add(message[0]);
                        }
                        if (message[0].contains("Teleport")) {
                            teleport.add(message[0]);
                        }
                    }
                }
                else {
                    swtch = false;
                }
            }
        }
    }

    public Item() {

    }
    public Item(String name, int dam, int stam) {
    	primaryName = name;
    	damage = dam;
    	stamina = stam;
    }
    public boolean goesBy(String name) {
        if (name.equals(primaryName))
            return true;
        return false;
    }

    public String getPrimaryName() {
        return primaryName;
    }
    public String getCombatType() {
    	return combatType;
    }
   //retrieve the int for health class for each verb(Jess)
    public int getWoundArray(String verb){
        int woundScore = 0;
        
        if(wound.contains(verb)){
            //get the wound score for specific verb
            String splitAgain = "";
            int index = wound.indexOf(verb);
            String toSplit = wound.get(index);
            
            for(int i=0; i<wound.size(); i++){
            if (toSplit.contains(", Dissapear")){
                //splitAgain = toSplit.split(","); 
                
                //keep splitting until i get wound int, then return it
                return woundScore;
            } else{
                //splitAgain = toSplit.split("(");
                return woundScore;
            }
        }
            
            woundScore = 1;
        }
        return woundScore;
    }
    public int getScoreArray(String verb){
        int newScore = 0;
        if(score.contains(verb)){
            String splitAgain = " ";
            int index = score.indexOf(verb);
            String toSplit = score.get(index);
            for(int i=0; i<score.size(); i++){
                if(toSplit.contains("Score(")){
                    String[] scoreArray = toSplit.split(" ");
                    newScore = Integer.parseInt(scoreArray[1]);
                    newScore += newScore;
                }
            }
        }return newScore;
    }
        
    public String getMessageForString(String verb) {
        String returnString = null;
        String[] messageArray = messages.keySet().toArray(new String[messages.size()]);
        for (int i = 0; i < messages.size(); i++) {
            if (messageArray[i].startsWith(verb)) {
                returnString = messages.get(messageArray[i]); 
            }
        }
        for (int i = 0; i < die.size(); i++){
            if (die.get(i).startsWith(verb)){
                System.out.println(returnString);
                 Die die = new Die();
                 die.hasDied();
            }
        }
        for (int i = 0; i < dissapear.size(); i++){
            if (dissapear.get(i).startsWith(verb)){
                GameState gs = GameState.instance();
                gs.dissapear(this);
            }
        }
        for (int i = 0; i < teleport.size(); i++){
            if (teleport.get(i).startsWith(verb)) {
                GameState gs = GameState.instance();
                gs.teleport();
            }
        }
        for (int i = 0; i < transform.size(); i++) {
        	if (transform.get(i).startsWith(verb)) {
        		String[] temp = transform.get(i).split("Transform\\(");
        		String[] itemName = temp[1].split("\\)");
        		GameState gs = GameState.instance();
        		gs.transform(this, itemName[0]);
        	}
        }
        for (int i = 0; i < score.size(); i++) {
        	if (score.get(i).startsWith(verb)) {
        		String[] temp = score.get(i).split("Score\\(");
        		String[] scoreVal = temp[1].split("\\)");
        		GameState gs = GameState.instance();
        		gs.addScore(scoreVal[0]);
        	}
        }
        for (int i = 0; i < wound.size(); i++) {
        	if (wound.get(i).startsWith(verb)) {
        		String[] temp = wound.get(i).split("Wound\\(");
        		String[] woundVal = temp[1].split("\\)");
        		GameState gs = GameState.instance();
        		gs.addWound(woundVal[0]);
        	}
        }
        for (int i = 0; i < win.size(); i++) {
        	if (win.get(i).startsWith(verb)) {
        		GameState gs = GameState.instance();
        		System.out.println(returnString);
        		gs.Win();
        	}
        }
        return returnString;
    }
    public int getDamage() {
    	return damage;
    }
    public int getStamina() {
    	return stamina;
    }
    public boolean getPrim() {
    	return primary;
    }
    //public String toString(){
    //  return null;
    //}
    
    // //retrieve the string for transform class(Jordan)
    // public int getTransformArray(String verb){
        // String transform = " ";
        // if(transform.contains(verb)){
            // //get the wound score for specific verb
            // String splitAgain = " ";
            // int index = transform.indexOf(verb);
            // String toSplit = transform.get(index);
            
            // for(int i=0; i<transform.size(); i++){
                // if (toSplit.contains(", Transform")){
                    // splitAgain = toSplit.split(Pattern.quote(","));
                    // //find object in arraylist and detlete it 
                // } else{
                    // //
                // }
            // }
        // }
        // return transform;
    // }
}
