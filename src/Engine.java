import java.util.*;
import java.io.*;
public class Engine {
	Scanner in = new Scanner(System.in);
	private boolean swtch = true;
	public Engine() {
		
	}
	
	public void engineLoop() {
		while(swtch) {
			System.out.print("Enter user input:");
			String command = in.nextLine();
			if (command.equals("help")) {
				help();
			} else if(command.startsWith("load")){
				if(command.startsWith("load ")) {
					try {	
						String[] temp = command.split(" ");
						load(temp[1]);
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Invalid syntax for 'load' command");
					}
				} else {
					System.out.println("I don't know what to load");
				}
			} else if(command.equals("q")) {
				System.out.println("Terminating bork engine");
				System.exit(0);
			} else if(command.equals("dir")){
				dir();
			} else if(command.startsWith("delete")) {
				delete(command);
			} else if (command.substring(command.lastIndexOf(".")+1).equals("sav") || command.substring(command.lastIndexOf(".")+1).equals("bork")) {
				load(command);
			} else if (command.equals("ll")){
				quickLoad();
			} else {
				System.out.println(command + " is not a valid command. Try typing 'help' for a list of valid commands");
			}
		}
	}
	
	public void help() {
		System.out.println("'q'-Quits the bork engine");
		System.out.println("'dir'-List the .sav and .bork files in the current directory");
		System.out.println("'load (fileName)'-Load the desired .sav or .bork file");
		System.out.println("'ll'-Load the last file opened");
		System.out.println("'delete (fileName.sav)'-Deletes user specified .sav file");
	}
	
	public void load(String fileName) {
		PrintWriter pw;
		if (fileName.substring(fileName.lastIndexOf(".")+1).equals("bork")) {
			Dungeon Dungeon1 = new Dungeon(fileName, true);
		    GameState gs = GameState.instance();
			gs.initialize(Dungeon1);
			gs.setInitFileName(fileName);
			swtch = false;
			try {
				pw = new PrintWriter("LastFile.txt");
				pw.println(fileName);
				pw.close();
			} catch (Exception e) {}
		} else if (fileName.substring(fileName.lastIndexOf(".")+1).equals("sav")) {
		    GameState gs = GameState.instance();
		    gs.setInitFileName(fileName);
			gs.restore(fileName);
			swtch = false;
			try {
				pw = new PrintWriter("LastFile.txt");
				pw.println(fileName);
				pw.close();
			} catch (Exception e) {}
		} else {
			System.out.println(fileName + " file is not a valid format. Please enter a file with a .bork or .sav extension.");
		}
	}
	public void quickLoad() {
		try {
			FileReader fr = new FileReader("LastFile.txt");
			Scanner in = new Scanner(fr);
			load(in.nextLine());
			fr.close();
			in.close();
		} catch (Exception e) {
			System.out.println("You have not opened any files to be cached yet.");
		}
	}
	public void dir() {
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile() && ((listOfFiles[i].getName().substring(listOfFiles[i].getName().lastIndexOf(".")+1).equals("bork")) || (listOfFiles[i].getName().substring(listOfFiles[i].getName().lastIndexOf(".")+1).equals("sav")))) {
		        System.out.println((i+1) + "   " + listOfFiles[i].getName());
		      }
		    }
	}
	
	public void delete(String fileName) {
			Command del = new DeleteCommand(fileName);
			del.execute();
	}

}