/**
 * This is an abstract class that uses the execute() method
 * to take on the actions and commands by the adventurer.
 * @author (Jessica Thomas)
 * @version (April 2, 2018)
 */

abstract class Command {
	private String input;
	Command(){
		
	}
	Command(String input) {
		this.input = input;
	}
	abstract String execute();

}
