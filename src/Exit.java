

import java.util.*;
/**
 * This class is in charge of creating and holding exits for
 * each individual room in Bork, along with their descriptions.
 * @author (Green Group Bork)
 * @see .bork file
 * @version (April 2, 2018)
 * 
 */
public class Exit {
	private String dir;
	private Room src;
	private Room dest;
	String tempVar;
	private String title;
	
	public Exit(Scanner s, Dungeon d) {
		tempVar = s.nextLine();
		if (!tempVar.equals("===")) {
			src = d.getRoom(tempVar);
			dir = s.nextLine();
			dest = d.getRoom(s.nextLine());
			s.nextLine();
		}
	}
	Exit(String dir, Room src, Room dest) {
		this.dir = dir;
		this.src = src;
		this.dest = dest;
	}
	public String descibe() {
		String exitDesc = dest.getTitle();
		if (dir.equals("n")) {
			exitDesc += " is to the NORTH. ";
		} else if (dir.equals("s")) {
			exitDesc += " is to the SOUTH. ";
		} else if (dir.equals("e")) {
			exitDesc += " is to the EAST. ";
		} else if (dir.equals("w")) {
			exitDesc += " is to the WEST. ";
		} else if (dir.equals("u")) {
			exitDesc += " is above(UP). ";
		} else if (dir.equals("d")) {
			exitDesc += " is below(DOWN). ";
		} 
		return exitDesc;
	}
	public String getDir() {
		return dir;
	}
	public Room getSrc() {
		return src;
	}
	public Room getDest() {
		return dest;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	
	
	class NoExitException{
		
	}
	/**
	 * This method will determine whether an exit is accesible
	 * or not.
	 * 
	 * @return boolean
	 */
	public boolean closedExit(){
	    return true;
	}
}
