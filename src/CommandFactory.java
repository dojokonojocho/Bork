/**
 * This class is in charge of the user's commands.
 * @author (Jessica Thomas)
 * @version (April 2, 2018)
 */
import java.util.*;
class CommandFactory {
    private static CommandFactory c = null;
    private CommandFactory() {
        
    }
    public static CommandFactory instance() {
        if (c == null) {
            c = new CommandFactory();
        }
        return c;
    }

    public void parse(String commandString) {
        Command userInput = null;
            if ((commandString.equals("n"))||(commandString.equals("s"))||(commandString.equals("e"))||(commandString.equals("w"))||(commandString.equals("u"))||(commandString.equals("d"))) {
                userInput = new MovementCommand(commandString);
            } else if (commandString.startsWith("save")){
                userInput = new SaveCommand(commandString);
            } else if (commandString.equals("help")) {
                userInput = new HelpCommand();
            } else if (commandString.startsWith("delete")) {
                userInput = new DeleteCommand(commandString);
            } else if (commandString.equals("qq")) {
                userInput = new QuickExitCommand();
            } else if (commandString.equals("i") || commandString.equals("inventory")) {
                userInput = new InventoryCommand();
            } else if (commandString.startsWith("take")) {
                userInput = new TakeCommand(commandString);
            } else if (commandString.startsWith("drop")) {
                userInput = new DropCommand(commandString);
            } else if (commandString.equals("score")){
                userInput = new ScoreCommand(commandString);
            } else if (commandString.equals("health") || commandString.equals("h")){
                userInput = new HealthCommand();
            } else if (commandString.startsWith("equip") || commandString.startsWith("eq")) {
            	userInput = new EquipCommand(commandString);
            } else if (commandString.startsWith("unequip") || commandString.startsWith("uq")) {
            	userInput = new UnequipCommand(commandString);
            } else if (commandString.equals("time")){
                userInput = new ClockCommand();
            } else {
                String[] temp = null;
                boolean un = true;
                try {
                    temp = commandString.split(" ");
                    if (temp.length == 2) {
                    	GameState gs = GameState.instance();
                    	Set<String> keys = gs.getDungeon().items.keySet();
                        for (String key: keys) { 
                        	if (key.startsWith(temp[1])) {
                        		userInput = new ItemSpecificCommand(temp[0], temp[1]);
                        		un = false;
                        	}
                        }	
                        if (un) {
                        	keys = gs.getDungeon().npcs.keySet();
                        	for (String key: keys) {
                        		if (key.startsWith(temp[1])) {
                        			userInput = new NPCSpecificCommand(temp[0], temp[1]);
                        			un = false;
                        		}
                        	}
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    
              }                
              if (un)
                    userInput = new UnknownCommand(commandString);
            }
            if (userInput != null) {
                userInput.execute();
            }
    }
}
