import java.util.*;
/**
 * Allows for other, non-playered controlled characters to exist within the dungeon that the user can interact with.
 * @author Dan Glista
 *	
 */
public class NPC {
	private String name;
	private Hashtable<Integer, String> messages = new Hashtable<Integer, String>();
	private int health;
	private int damage;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int influence = 0;
	private int stamina = 10;
	private boolean hostile;
	private int info = 0;
	private String desc;
	private String battleCry;
	/**
	 * 
	 * @param sc 
	 * The contructor takes in a Scanner that reads from the .bork file initializing the NPC
	 */
	public NPC(Scanner sc, Dungeon thisDungeon) {
		String temp = sc.nextLine();
		name = temp;
		if (!name.equals("===")) {
			String[] healthArray = sc.nextLine().split(":");
			health = Integer.valueOf(healthArray[1]);
			String[] damageArray = sc.nextLine().split(":");
			damage = Integer.valueOf(damageArray[1]);
			String[] hostileArray = sc.nextLine().split(":");
			hostile = Boolean.valueOf(hostileArray[1]);
			String[] inventoryArray = sc.nextLine().split(":");
			if (inventoryArray[1].contains(",")){
				String[] items = inventoryArray[1].split(",");
				for (String item:items) {
					inventory.add(thisDungeon.getItem(item));
				}
			} else {
				inventory.add(thisDungeon.getItem(inventoryArray[1]));
			}
			String[] infoArray = sc.nextLine().split(":");
			info = Integer.valueOf(infoArray[1]);
			String[] descArray = sc.nextLine().split(":");
			desc = descArray[1];
			String[] cryArray = sc.nextLine().split(":");
			battleCry = cryArray[1];
			while	(!(temp = sc.nextLine()).equals("---")) {
				if (temp.startsWith("talk")) {
					String[] messageArray = temp.split(":");
					String[] intValArray = messageArray[0].split("\\[");
					if (intValArray[1].length() == 1) {
						messages.put(0, messageArray[1]);
					} else {
					String[] removeBracket = intValArray[1].split("\\]");
					messages.put(Integer.valueOf(removeBracket[0]), messageArray[1]);
					}
				}
			}
		}		
		
	}
	/**
	 * The user is able to retrieve messages from the NPC depending on the verbage the user uses.
	 * @param s
	 * Takes in a String from the user representing a verb
	 * @return The string message that will be displayed to the user
	 */
	public String getMessage() {
		Set<Integer> keys = messages.keySet();
		int placeHolder = 0;
		for (int key: keys) {
			if (influence >= 0) {
				if (influence >= key) {
					if (key > placeHolder) {
						placeHolder = key;
					}
				}
			} else {
				if (influence <= key) {
					if (key < placeHolder) {
						placeHolder = key;
					}
				}
			}
		}
		return messages.get(placeHolder);
	}
	public String getStatus() {
		String status = name;
		status += "\nRelationship: ";
		if (influence >= 75) {status += "Great ally";} 
		else if(influence >= 50) {status += "Good friend";	}
		else if(influence >= 25) {status += "Acquaintance";}
		else if(influence > -10) {status += "Indifferent";}
		else if(influence > -30) {status += "Minor enemy";}
		else if(influence > -60) {status += "Major ememy";}
		else {status += "Great enemy";}
		if (info >= 1) {status += "\nDescription: " + desc;}
		if (info >= 2) {status += "\nInventory: ";
			if (inventory == null) {status += name + " is not carrying anything";}
			else {
				for (int i = 0; i < inventory.size(); i++) {
					if (i > 0) {
						status += ", ";
					}
					status += inventory.get(i).getPrimaryName();
				}
			}
		}
		if (info >= 3) {status += "\nHealth: " + health;}
		if (info >= 4) {status += "\nDamage: " + damage;}	
		return status;
	}
	/**
	 * An way for other classes to interact with the inventory of the NPC. Will be used for when the NPC dies or gives an Item.
	 * @return the ArrayList inventory for the NPC that contains any Item objects the NPC may be holding.
	 */
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	/**
	 * Uses a rudimentary fighting system between the user and NPC. 
	 */
	public void fight() {
		
	}
	public void setHealth(int h) {
		this.health = h;
	}
	public void changeHealth(int h) {
		this.health -= h;
	}
	public void setInfluence(int i) {
		this.influence = i;
	}
	public String getName() {
		return name;
	}
	public int getInfluence() {
		return influence;
	}
	public int getHealth() {
		return health;
	}
	public String getCry() {
		return battleCry;
	}
	public int getInfo() {
		return info;
	}
	public void setInfo(int i) {
		this.info = i;
	}
	public int getStamina() {
		return stamina;
	}
	public void setStamina(int s) {
		this.stamina = s;
	}
}
