import java.io.PrintWriter;
import java.util.*;
import java.util.*;
public class Room {
	private String title = "";
	private String desc = "";
	private String listOfExits;
	private boolean beenHere = false;
	private ArrayList<Exit> exits = new ArrayList<Exit>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<NPC> npcs = new ArrayList<NPC>();
	
	public Room(Scanner s, Dungeon thisDungeon, boolean initState) {
		if (s.hasNextLine()) {
			title = s.nextLine();
		}
		if (!title.equals("===")) {
			String temp = s.nextLine();
			if (temp.startsWith("Contents:") && initState) {
				String[] throwAway = temp.split(" ");
				if (throwAway[1].contains(",")) {
					String[] listOfItems = throwAway[1].split(",");
					for (int i = 0; i < listOfItems.length; i++) {
						items.add(thisDungeon.getItem(listOfItems[i]));
					}
				} else {
					String itemName = throwAway[1];
					items.add(thisDungeon.getItem(itemName));
				}
				temp = s.nextLine();
			}
			GameState gs = GameState.instance();
			if (!initState && temp.startsWith("Contents:")) {
				if (gs.getInitFileName().substring(gs.getInitFileName().lastIndexOf(".")+1).equals("sav")) {
					temp = s.nextLine();
				}
			}
			if (temp.startsWith("NPCs:") && initState) {
				String[] throwAway = temp.split(":");
				if (throwAway[1].contains(",")) {
					String[] listOfNPCs = throwAway[1].split(",");
					for (int i = 0; i < listOfNPCs.length; i++) {
						npcs.add(thisDungeon.getNPC(listOfNPCs[i]));
					}
				} else {
					String npcName = throwAway[1];
					npcs.add(thisDungeon.getNPC(npcName));
				}
				temp = s.nextLine();
			}
			boolean swtch = true;
			boolean first = true;
			while (swtch) {
				if (!first) {
					temp = s.nextLine();
				} else {
					first = false;
				}
				if (!temp.equals("---")) {
					setDesc(temp + " ");
				} else {
					swtch = false;
				}
			}
		}
	}
	public Room(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public ArrayList<NPC> getNPCs(){
		return npcs;
	}
	public void setListOfExits(String listOfExits) {
		this.listOfExits = listOfExits;
	}
	public void setDesc(String tempDesc) {
		this.desc += tempDesc;
	}
	public String describe() {
		System.out.println();
		String returnDesc = title;
		if (beenHere == false) {
			returnDesc += "\n" + desc;
			beenHere = true;
		}
		returnDesc += "\n" + descExits(exits);
		if (!items.isEmpty()) {
			returnDesc += "\n" + descItems();
		}
		if (!npcs.isEmpty()) {
			returnDesc += "\n" + descNPCs();
		}
		return returnDesc;
		
	}
	void add(Exit exit) {
		
	}
	void remove(Item item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				items.remove(i);
			}
		}
	}
	void addItem(Item item) {
		items.add(item);
	}
	void removeNPC(NPC npc) {
		Iterator<NPC> itr = npcs.iterator();
		while (itr.hasNext()) {
			if (itr.next() == npc){
				itr.remove();
			}
		}
	}
	public Item getItemNamed(String name) {
		GameState gs = GameState.instance();
		ArrayList<String> invString = gs.getInventoryNames();
		if (invString.contains(name)) {
			System.out.println("You are already carrying " + name);
		} else if (!items.isEmpty()) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getPrimaryName().equals(name)) {
					return items.get(i);
				}
			}
			System.out.println("There is no " + name + " here");
		} else {
			System.out.println("There are no items here");
		}
		return null;
	}
	ArrayList<Item> getContent(){
		return items;
	}
	public String descExits(ArrayList<Exit> exits) {
		String exitDesc = "";
		for (int i = 0; i < exits.size(); i++) {	
			exitDesc += exits.get(i).descibe();
		}
		return exitDesc;
	}
	public String descItems() {
		String itemDesc = "You a see ";
		for (int i = 0; i < items.size(); i++) {
			if (i == 0) {
				if (items.get(i).getPrimaryName().startsWith("a") || items.get(i).getPrimaryName().startsWith("e") || items.get(i).getPrimaryName().startsWith("i") || items.get(i).getPrimaryName().startsWith("o") || items.get(i).getPrimaryName().startsWith("u")) {
					itemDesc += "an ";
				} else {
					itemDesc += "a ";				
				}
			}
			if ((i > 0) && (i < items.size()-1)) {
				itemDesc += ", ";
			} else if ((i > 0) && (i == items.size()-1)) {
				itemDesc += ", and ";
			}
			itemDesc += items.get(i).getPrimaryName();
		}
		return itemDesc += ".";
	}
	public String descNPCs() {
		String npcDesc = "There is ";;
		for (int i = 0; i < npcs.size(); i++) {
			if (i == 0) {
				if (npcs.get(i).getName().startsWith("a") || npcs.get(i).getName().startsWith("e") || npcs.get(i).getName().startsWith("i") || npcs.get(i).getName().startsWith("o") || npcs.get(i).getName().startsWith("u")){
					npcDesc += "an ";
				} else {
					npcDesc += "a ";				
				}
			}
			if ((i > 0) && (i < npcs.size()-1)) {
				npcDesc += ", ";
			} else if ((i > 0) && (i == npcs.size()-1)) {
				npcDesc += ", and ";
			}
			npcDesc += npcs.get(i).getName();
		}
		return npcDesc += " here.";
	}
	public Room leaveBy(String dir) {
		for (int i = 0; i < exits.size(); i++) {
			if (exits.get(i).getDir().equals(dir)) {
				System.out.println(exits.get(i).getDest().describe());
				return exits.get(i).getDest();
			}
		}
		System.out.println(this.getTitle() + " has no exit in that direction");
		return exits.get(0).getSrc();
	}
	public void addExit(Exit exit) {
		exits.add(exit);
	}
	void storeState(PrintWriter pw) {
		pw.println(this.getTitle() + ":");
		pw.println("beenHere=" + beenHere);
		if (!items.isEmpty()) {
			String setOfItems = "Contents: ";
			for (int i = 0; i < items.size(); i++) {
				if (i > 0) {
					setOfItems += ",";
				}
				setOfItems += items.get(i).getPrimaryName();
			}
			pw.println(setOfItems);
		}
		if (!npcs.isEmpty()) {
			String setOfNpcs = "NPCs: ";
			for (int i = 0; i < npcs.size(); i++) {
				if (i > 0) {
					setOfNpcs += ",";
				}
				setOfNpcs += npcs.get(i).getName() + "(" + npcs.get(i).getHealth() + ":" + npcs.get(i).getInfluence() + ":" + npcs.get(i).getInfo() + ")" ;
			}
			pw.println(setOfNpcs);
		}
		pw.println("---");
	}
	void restoreState(Scanner sc, Dungeon d) {
		String[] beenHereArray = sc.nextLine().split("=");
		if (beenHereArray[1].equals("true")) {
			beenHere = true;
		} else if(beenHereArray[1].equals("false")) {
			beenHere = false;
		} else {
			System.out.println("No boolean value could be found for" + this.getTitle());
		}
		String temp = sc.nextLine();
		if (temp.startsWith("Contents: ")) {
			String[] throwAway = temp.split(" ");
			if (throwAway[1].contains(",")) {
				String[] itemList = throwAway[1].split(",");
				for (int i = 0; i < itemList.length; i++) {
					items.add(d.getItem(itemList[i]));
				}
			} else {
				String itemName = throwAway[1];
				items.add(d.getItem(itemName));
			}
			temp = sc.nextLine();
		}
		if (temp.startsWith("NPCs: ")) {
			String[] throwAway = temp.split(" ");
			if (throwAway[1].contains(",")) {
				String[] npcList = throwAway[1].split(",");
				for (int i = 0; i < npcList.length; i++) {
					String[] name = npcList[i].split("\\(");
					npcs.add(d.getNPC(name[0]));
					String[] params = npcList[1].split("\\)");
					String[] param = params[0].split(":");
					npcs.get(i).setHealth(Integer.valueOf(param[0]));
					npcs.get(i).setInfluence(Integer.valueOf(param[1]));
					npcs.get(i).setHealth(Integer.valueOf(param[2]));
				}
			} else {
				String stuff = throwAway[1];
				String[] npcInfo = stuff.split("\\(");
				npcs.add(d.getNPC(npcInfo[0]));
				String[] params = npcInfo[1].split(":");
				npcs.get(0).setHealth(Integer.valueOf(params[0]));
				npcs.get(0).setInfluence(Integer.valueOf(params[1]));
				npcs.get(0).setHealth(Integer.valueOf(params[2].substring(0, 1)));
			}
			sc.nextLine();
		}
	}
	public ArrayList<Item> getItems(){
		return items;
	}
	class NoRoomException{
		
	}
	
	
}
