import java.io.*;
import java.util.*;
/**
 * 
 * The interpreter class contains the main() method and is main organization of the entire bork program 
 *
 */
class Interpreter {
	public static void main(String[] args) {
		
		
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);   
	   
		System.out.println("Entering Group Bork v1.0 engine. Type 'help' for a list of commands");
		Scanner in = new Scanner(System.in);
		
		
		Engine start = new Engine();
		start.engineLoop();
		
		
		inputLoop(br);
		
	}	

	private static void inputLoop(BufferedReader br) {
		Scanner in = new Scanner(System.in);
		CommandFactory cf = CommandFactory.instance();
		 String userInput = "";
		    do {
			userInput = promptUser(br);
			if (!userInput.equals("q"))
				cf.parse(userInput);
		    } while (!userInput.equals("q"));
		    GameState gs = GameState.instance();
		    if (gs.hasSaved == false) {	
		    	System.out.println("Your game is not saved. Are you sure you want to quit? (y/n)");
		    	String yesNo = in.next();
		    	if (yesNo.equals("y")) {
		    	System.out.println("Terminating bork engine");
		    	} else {
		    		inputLoop(br);
		    	}
		    } else {
		    	System.out.println("Terminating bork engine");	
		    }
		    in.close();
	}
	private static String promptUser(BufferedReader commandLine) {
		System.out.print(":");
		String userInput = "";
		try {
			String line = "";
	        while ((line = commandLine.readLine()) != null) {
	        	userInput += line;
	        	return userInput;
	        }

	        commandLine.close();
		} catch(IOException e) {
			System.err.println("No input found");
		}
		return userInput;
	}

}
