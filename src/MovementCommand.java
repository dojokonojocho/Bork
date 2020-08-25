import java.util.Scanner;
class MovementCommand extends Command{
    private String dir;

    MovementCommand(String dir) {
        this.dir = dir;
    }

    String execute() {
        if (dir.equals("n")) {
            GameState gs = GameState.instance();
            Room current = gs.getAdventurersCurrentRoom();
            Room newRoom = current.leaveBy("n");
            gs.setAdventurersCurrentRoom(newRoom);
            gs.hasSaved = false;
        } else if (dir.equals("s")) {
            GameState gs = GameState.instance();
            Room current = gs.getAdventurersCurrentRoom();
            Room newRoom = current.leaveBy("s");
            gs.setAdventurersCurrentRoom(newRoom);
            gs.hasSaved = false;
        } else if (dir.equals("e")) {
            GameState gs = GameState.instance();
            Room current = gs.getAdventurersCurrentRoom();
            Room newRoom = current.leaveBy("e");
            gs.setAdventurersCurrentRoom(newRoom);
            gs.hasSaved = false;
            System.out.println("The evil elf of the east took 1 health point!");
            gs.addWound("1");
        } else if (dir.equals("w")) {
            Scanner scnr = new Scanner(System.in);
            System.out.println("You cannot go this way until you answer a riddle correct.");
            System.out.println("If west is where you want to go, the riddle answers is what you need to know.");
            System.out.println("You get three chances, before you die. Be careful");
            String answerone = "soap";
            String answertwo = "fire";
            String answerthree = "footsteps";

            System.out.println("I'm not clothes but I cover your body; The more I'm used, the thinner I grow. What am I?");
            String one = scnr.nextLine();
            if(one.equals(answerone)){
                GameState gs = GameState.instance();
                Room current = gs.getAdventurersCurrentRoom();
                Room newRoom = current.leaveBy("w");
                gs.setAdventurersCurrentRoom(newRoom);
                gs.hasSaved = false;

            } else {

                System.out.println("Wrong, two more chances.");
                System.out.println("Feed me and I live, yet give me a drink and I die. What am I?");
                String two = scnr.nextLine();

                if(two.equals(answertwo)){
                    GameState gs = GameState.instance();
                    Room current = gs.getAdventurersCurrentRoom();
                    Room newRoom = current.leaveBy("w");
                    gs.setAdventurersCurrentRoom(newRoom);
                    gs.hasSaved = false; 
                }else{
                    System.out.println("Wrong Again, 1 more chance.");
                    System.out.println("The more you take, the more you leave behind. What am I?");
                    String three = scnr.nextLine();

                    if(three.equals(answerthree)){
                        GameState gs = GameState.instance();
                        Room current = gs.getAdventurersCurrentRoom();
                        Room newRoom = current.leaveBy("w");
                        gs.setAdventurersCurrentRoom(newRoom);
                        gs.hasSaved = false; 
                    }else{
                        Die died = new Die();
                        died.hasDied();
                    }
                }
            }
        } else if (dir.equals("u")) {
            GameState gs = GameState.instance();
            Room current = gs.getAdventurersCurrentRoom();
            Room newRoom = current.leaveBy("u");
            gs.setAdventurersCurrentRoom(newRoom);
            gs.hasSaved = false;
        } else if (dir.equals("d")) {
            GameState gs = GameState.instance();
            Room current = gs.getAdventurersCurrentRoom();
            Room newRoom = current.leaveBy("d");
            gs.setAdventurersCurrentRoom(newRoom);
            gs.hasSaved = false;
        }

        return null;
    }
}
