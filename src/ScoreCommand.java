
/**
 * Write a description of class ScoreCommand here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ScoreCommand extends Command{
    public ScoreCommand(String s){
    }
   
    String execute() {
    GameState gs = GameState.instance();
	System.out.println("Your score is:" + gs.getScore());
	return null;
    }
}


