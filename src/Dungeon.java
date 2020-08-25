import java.util.*;
import java.io.*;
public class Dungeon {
	private String name;
	private Room entry;
	private String fileName;
	private boolean initState;
	Hashtable<String, Room> rooms = new Hashtable<String, Room>();
	private ArrayList<Exit> exits = new ArrayList<Exit>();
	Hashtable<String, Item> items = new Hashtable<String, Item>();
	Hashtable<String, NPC> npcs = new Hashtable<String, NPC>();
	
	
	public Dungeon(String fileName, boolean initState) {
		this.fileName = fileName;
		this.initState = initState;
		parseBork();
	}
	Dungeon (Room entry, String name){
		this.entry = entry;
		this.name = name;
		rooms.put(entry.getTitle(), entry);
	}
	//public void init(Dungeon this, String fileName){
	//	
	//}
	public void parseBork() {
		if (fileName.substring(fileName.lastIndexOf(".")+1).equals("bork")) {
			try {
				FileReader fr = new FileReader(fileName);
				Scanner scnr = new Scanner(fr);
				
				if (scnr.hasNextLine()) {
					name = scnr.nextLine();
				} 
				if (scnr.hasNextLine()) {
					String version = scnr.nextLine();
					if (!version.equals("Group Bork v1.0")) {
						System.out.print("The .bork file is incompatible with this version of Bork\nTerminating Bork Engine");
						System.exit(0);
					}
				}
				scnr.nextLine();
				scnr.nextLine();
				boolean swtch = true;
				int itemCount = 0;
				while(swtch) {
					items.put("i" + itemCount, new Item(scnr));
					if (items.get("i" + itemCount).getPrimaryName().equals("===")) {
						items.remove("i" + itemCount);
						swtch = false;
					} else {
						items.put(items.get("i" + itemCount).getPrimaryName(), items.get("i" + itemCount)); 
						items.remove("i" + itemCount);
					}
					itemCount++;
				}
				items.put("fist", new Item("fist",1,1));
				scnr.nextLine();
				int npcCount = 0;
				swtch = true;
				while (swtch) {
					npcs.put("npc" + npcCount, new NPC(scnr, this));
					if (npcs.get("npc" + npcCount).getName().equals("===")) {
						npcs.remove("npc" + npcCount);
						swtch = false;
					} else {
						npcs.put(npcs.get("npc" + npcCount).getName(), npcs.get("npc" + npcCount));
						npcs.remove("npc" + npcCount);
					}
					npcCount++;
				}
				scnr.nextLine();
				swtch = true;
				int roomCount = 0;
				while (swtch) {
					rooms.put("r" + roomCount, new Room(scnr, this, initState));
					if (rooms.get("r" + roomCount).getTitle().equals("===")) {
						rooms.remove("r" + roomCount);
						swtch = false;
					} else {
					rooms.put(rooms.get("r" + roomCount).getTitle(), rooms.get("r" + roomCount)); 
					rooms.remove("r" + roomCount);
					}
					if (roomCount == 0) {
						Set<String> keySet = rooms.keySet();
						for (String key : keySet) {
							entry = rooms.get(key);
						}
					}
					roomCount++;
				}
				scnr.nextLine();
				swtch = true;
				int exitCount = 0;
				while (swtch) {
					exits.add(new Exit(scnr, this));
					if (exits.get(exitCount).tempVar.equals("===")) {
						exits.remove(exitCount);
						swtch = false;
					} else {
						rooms.get(exits.get(exitCount).getSrc().getTitle()).addExit(exits.get(exitCount));
					}
					exitCount++;
				}
				
				
				scnr.close();
				fr.close();
			} catch (FileNotFoundException e) {
				System.err.println(fileName + " could no be opened");
				System.exit(0);
			} catch (IOException e) {
				System.err.println("An IOException has occured");
			} 
		} else {
			System.err.println(fileName + " is not of type: .bork");
			System.exit(0);
		}
	}
	public Room getEntry() {
		return entry;
	}
	public String getName() {
		return name;
	}
	public void add(Room room) {
		rooms.put(room.getTitle(), room);
	}
	public Room getRoom(String roomTitle) {
		return rooms.get(roomTitle);
	}
	public Item getItem(String primaryName) {
		return items.get(primaryName);
	}
	public NPC getNPC(String name) {
		return npcs.get(name);
	}
	public void add(Item item) {
		
	}
	public String getFileName() {
		return fileName;
	}
	void storeState(PrintWriter pw) {
		pw.println("Dungeon file: " + fileName);
		pw.println("Room states:");
		Set<String> listOfRooms = rooms.keySet();
		for (String key : listOfRooms) {
			rooms.get(key).storeState(pw);
		}
		pw.println("===");
	}
	void restoreState(Scanner sc) {
		sc.nextLine();
		boolean swtch = true;
		String roomName = "";
		while (swtch) {
			roomName = sc.nextLine();
			if (!roomName.equals("===")) {
				rooms.get(roomName.substring(0, roomName.length()-1)).restoreState(sc, this);;
			} else {
				swtch = false;
			}
		}
	}
}
