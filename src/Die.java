/**
 * Class that is initialized during the event of the users death. It's own class as there can be multiple sources of death. 
 * @author Daniel
 */
public class Die {
	/**
	 * Empty constructor for organization sake, serves no real purpose
	 */
	public Die() {}
	/**
	 * Will display a message to the user denoting they have died 
	 */
	public void hasDied() {
		GameState gs = GameState.instance();
		System.out.println("You have finished the game with a score of " + gs.getScore() + ".");
		System.out.println("Terminating Bork Engine");
		System.exit(0);
		
	}
}
