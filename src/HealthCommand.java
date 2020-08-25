
/**
 * This class will determine the players health, while also updating their overall
 * Health status as they play the game. It is determined based on the actions the 
 * player takes.
 *
 * @author (Jessica Thomas)
 * @param an integer health
 * @version (April 2, 2018)
 * Just testing the push
 */
public class HealthCommand extends Command
{
    //variables
    
    //Constructor
    HealthCommand(){
        
    }
    
    //change health to the method I will create in Health class
    String execute() {
    	GameState gs = GameState.instance();
    	int health = gs.getHealth();
        if (health >= 8){
        	System.out.println("You are strong and powerful!");
        } else if(health >= 5){
        	System.out.println("You might need a snack but you're good.");
        } else if(health >= 3){
            System.out.println("You are in danger of dying! Eat something!");
        } else{
            System.out.println("You are practically dead!");
        }
        return null;
    }
}
