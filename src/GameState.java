
/**
 * This class will now keep track of the time that user spends playing the game base
 * based on the movements that they take. It will also keep track of the total score.
 * @author (Ebony Clarke)
 * @param an integer total score
   @param an integer counter
 * @version (April 2, 2018)
 */
import java.util.*;
import java.io.*;
class GameState {
   private Room currentRoom;
   private Dungeon currentDungeon;
   private ArrayList<Item> inventory = new ArrayList<Item>();
   private ArrayList<Item> equipment = new ArrayList<Item>();
   private String initFileName;
   public boolean hasSaved = true;
   private boolean initEntry = true;
   private static GameState g = null;
   private int score = 0;
   private int MAX_HEALTH = 10;
   private int health = MAX_HEALTH;
   private int stamina = 10;
   private long startTime;
   private long elapsedTime;
   private long elapsedSec;
   private long elapsedMin;
   private GameState() {
    
   }
   public static GameState instance() {
      if (g == null) {
         g = new GameState();
      }
      return g;
   }
    
   public void initialize(Dungeon dungeon) {
      currentDungeon = dungeon;
      if (initEntry) {
         currentRoom = currentDungeon.getEntry();
         startTime = System.currentTimeMillis();
      }
      System.out.println("Welcome adventurer to the " + dungeon.getName() + " dungeon");
      System.out.println(currentRoom.describe());
      System.out.println("Type 'help' for a list if commands.");
   }
   public Room getAdventurersCurrentRoom() {
      return currentRoom;
   }
   public void setAdventurersCurrentRoom(Room room) {
      currentRoom = room;
   }
   public String getInitFileName() {
      return initFileName;
   }
   public void setInitFileName(String name) {
      this.initFileName = name;
   }
   public Dungeon getDungeon() {
      return currentDungeon;
   }
   ArrayList<String> getInventoryNames(){
      ArrayList<String> invString = new ArrayList<String>();
      for (int i = 0; i < inventory.size(); i++) {
         invString.add(inventory.get(i).getPrimaryName());
      }
      return invString;
   }
   void addToInventory(Item item){
      inventory.add(item);
   }
   void removeFromInventory(Item item) {
      for (int i = 0; i < inventory.size(); i++) {
         if (inventory.get(i) == item) {
            inventory.remove(i);
         }
      }
   }
   void removeFromRoom(Item item){
       for (int i = 0; i < currentRoom.getItems().size(); i++) {
           if (currentRoom.getItems().get(i) == item) {
               currentRoom.getItems().remove(i);
           }
       }   
   }
   void Win(){
      System.out.println("Congratulations! You have beaten the " + currentDungeon.getName() + " dungeon.");
      System.exit(0);
   }
   Item getItemInVicinityNamed(String name) {
      if (!inventory.isEmpty()) {
         for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getPrimaryName().equals(name)) {
               return inventory.get(i);
            }
         }
      } else {
        
      }
      Item item = getAdventurersCurrentRoom().getItemNamed(name);
      return item;
   }
   Item getItemInInventoryNamed(String name) {
      if (!inventory.isEmpty()) {
         for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getPrimaryName().equals(name)) {
               return inventory.get(i);
            }
         }
         System.out.println("You are not holding a " + name);
      } else {
         System.out.println("You are not holding any item");
      }
      return null;
   }
   public void eqiupItem(Item item) {
	   for (Item i:equipment) {
		   if (i == item) {
			   System.out.println("You have already equiped " + i.getPrimaryName() + ".");
			   return;
		   } else if(i.getPrim()) {
			   System.out.println("You already equiped have a primary weapon equiped (" + i.getPrimaryName() + ").");
		   }
	   }
	   if (item.getCombatType().equals("melee")) {
		   equipment.add(item);
		   System.out.println("You have equiped " + item.getPrimaryName() + ".");
	   }
   }
   public void unequipItem(Item item) {
       Iterator<Item> itr = equipment.iterator();
       while (itr.hasNext()) {
           Item i = itr.next();
           if (i == item) {
               itr.remove();
           }
       }
   }
   public Item getEquipedItem(String name) {
       for (Item i:equipment) {
           if (i.getPrimaryName().equals(name)) {
               return i;
           }
       }
       return null;
   }
   public Item getPrimaryWeapon() {
	   for (Item i:equipment) {
		   if (i.getPrim()) {
			   return i;
		   }
	   }
	   return null;
   }
   NPC getNPCInRoomNamed(String name) {
       for (int i = 0; i < getAdventurersCurrentRoom().getNPCs().size(); i++) {
           if (getAdventurersCurrentRoom().getNPCs().get(i).getName().equals(name)) {
               return getAdventurersCurrentRoom().getNPCs().get(i);
           }
       }
       System.out.println(name + "is not in this room");
       return null;
   }
   String showInventory() {
      String inv = "Inventory: ";
      if (!inventory.isEmpty()) {
         int j = 0;
         for (int i = 0; i < inventory.size(); i++) {
            if (j > 0) {
               inv += ",";
            }
            if (!equipment.contains(inventory.get(i))) {
                inv += inventory.get(i).getPrimaryName();
                j++;
            }
         }
         if (!equipment.isEmpty()) {
             for (int i = 0; i < equipment.size(); i++) {
                 inv += "\n";
                 if (equipment.get(i).getPrim()) {
                     inv += "Primary Weapon: " + equipment.get(i).getPrimaryName();
                 }
             }
         }
         return inv;
      }
      return "You are currently holding nothing";
   }
   int getHealth() {
       return health;
   }
   void addWound(String woundVal) {
       int val = Integer.valueOf(woundVal);
       health -= val;
       if (health > MAX_HEALTH) {
           health = MAX_HEALTH;
       }
       if (health < 1) {
           Die die = new Die();
           die.hasDied();
       }
   }
   void addScore(String scoreVal) {
       int val = Integer.valueOf(scoreVal);
       score += val;
   }
   void transform(Item currentItem, String newItemName) {
       boolean inRoom = false;
       for (int i = 0; i < getAdventurersCurrentRoom().getItems().size(); i++) {
           //System.out.println(getAdventurersCurrentRoom().getItems().get(i).getPrimaryName());
           if (currentItem == getAdventurersCurrentRoom().getItems().get(i)) {
               getAdventurersCurrentRoom().addItem(currentDungeon.getItem(newItemName));
               inRoom = true;
           }
       } 
       if (!inRoom) {
           inventory.add(currentDungeon.items.get(newItemName));
       }
       dissapear(currentItem);
   }
   void store(String saveName) {
      try {
         PrintWriter pw = new PrintWriter(saveName);
         pw.println("Group Bork v1.0");
         currentDungeon.storeState(pw);
         pw.println("Adventurer:");
         pw.println("Current room: " + this.getAdventurersCurrentRoom().getTitle());
         String listOfItems = "Inventory: ";
         if (inventory != null){
            for (int i = 0; i < inventory.size(); i++) {
               if (i > 0) {
                  listOfItems += ",";
               }
               listOfItems += inventory.get(i).getPrimaryName();
            }
            pw.println(listOfItems);
         }
         pw.println("Health: " + health);
         pw.println("Score: " + score);
         pw.close();
      } catch (IOException e) {
         System.err.println("An IOException occured while saving");
      }
      try        
      {
         for (int i = 0; i < 11; i++) {
            Thread.sleep(90);
            System.out.print(".");
         }
         Thread.sleep(90);
         System.out.print("\n");
         Thread.sleep(1200);
         System.out.println("File successfully saved to " + saveName);
      } 
      catch(InterruptedException ex) 
      {
         Thread.currentThread().interrupt();
      }
    
   }
   void restore(String fileName) {
      try {
         FileReader fr = new FileReader(fileName);
         Scanner sc = new Scanner(fr);
         if (!sc.nextLine().equals("Group Bork v1.0")) {
            System.out.println("The .sav file is incompatible with this version of Bork");
            System.out.println("Terminating bork engine");
            System.exit(0);
         }
         String[] dngName = sc.nextLine().split(":");
         String borkName = dngName[1].substring(1);
         currentDungeon = new Dungeon(borkName, false);
         currentDungeon.restoreState(sc);
         sc.nextLine();
         String[] currentRoomArray = sc.nextLine().split(":");
         currentRoom = currentDungeon.getRoom(currentRoomArray[1].substring(1));
         try {
            String[] invTemp = sc.nextLine().split(" ");
            if (invTemp[1].contains(",")) {
               String[] inv = invTemp[1].split(",");
               for (String s : inv) {
                  inventory.add(currentDungeon.getItem(s));
               }
            } else {
               inventory.add(currentDungeon.getItem(invTemp[1]));
            }
         } catch (IndexOutOfBoundsException e) {
            
         }
         String[] healthParam = sc.nextLine().split(" ");
         health = Integer.valueOf(healthParam[1]);
         String[] scoreParam = sc.nextLine().split(" ");
         score = Integer.valueOf(scoreParam[1]);
         initEntry = false;
         sc.close();
      } catch (IOException e) {
         System.err.println("File not found");
         System.exit(0);
      } 
      this.initialize(currentDungeon);
   }
   /**
     * This method will return the user's score when prompted 
     * @return Method will return an integer: the score
     */
   public int getScore(){
      return score;
   }
   public int getStamina() {
       return stamina;
   }
    /**
     * This method will return the user's time when prompted 
     * @return Method will return an integer: the time
     */
   public String getTime(){
      elapsedTime = System.currentTimeMillis() - startTime;
      elapsedSec = elapsedTime/1000;
      long seconds = elapsedSec % 60;
      elapsedMin = elapsedSec/60;
      String time = "";
      if (elapsedMin > 1) {
    	  time += elapsedMin + " mins ";
      } else if(elapsedMin > 0) {
    	  time += elapsedMin + " min ";
      }
      if (seconds > 1) {
    	  time += seconds + " secs";
      } else {
    	  time += seconds + " sec";
      }
      return time;                  
    }
   public void teleport() {
       String[] keys = currentDungeon.rooms.keySet().toArray(new String[currentDungeon.rooms.size()]);
       String randKey = keys[new Random().nextInt(currentDungeon.rooms.size())];
       setAdventurersCurrentRoom(currentDungeon.rooms.get(randKey));
       System.out.println(getAdventurersCurrentRoom().describe());
   }
   
   //removes item when Drink, Recycle, and Eat 
   public void dissapear(Item i){
       removeFromInventory(i);
       removeFromRoom(i);
       //See line 74
   }
}
