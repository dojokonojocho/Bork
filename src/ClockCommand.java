
/**
 * Write a description of class ClockCommand here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ClockCommand extends Command{
    public ClockCommand(){
    }
    String execute() {
    GameState gs = GameState.instance();
    System.out.println("Your elapsed time is " + gs.getTime());
    return null;
    }
}
