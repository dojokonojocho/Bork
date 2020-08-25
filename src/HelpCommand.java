
class HelpCommand extends Command{	
	HelpCommand(){
	}
	String execute() {
		int time = 50;
		try {
		System.out.println("LIST OF COMMANDS");
		Thread.sleep(time);
		System.out.println("MOVEMENT:  'n'-NORTH 's'-SOUTH 'w'-WEST 'e'-EAST 'u'-UP 'd'-DOWN ");
		Thread.sleep(time);
		System.out.println("PLAYER:    'health','h'-Displays the player's current health status");
		Thread.sleep(time);
		System.out.println("           'inventory','i'-Displays the player's current held items");
		Thread.sleep(time);
		System.out.println("           'score'-Displays the players current score");
		Thread.sleep(time);
		System.out.println("ITEMS:     'take <item>'-Adds item from current room to your inventory");
		Thread.sleep(time);
		System.out.println("           'drop <item>'-Drops item form your inventory to the current room");
		Thread.sleep(time);
		System.out.println("           '<verb> <item>'-Interact with an item in your inventory or in the cuurent room");
		Thread.sleep(time);
		System.out.println("           'equip <item>','eq <item>'-Equips an item for battle if possible");
		Thread.sleep(time);
		System.out.println("           'unequip <item>','uq <item>-Unequips the disired item if currently equiped'");
		Thread.sleep(time);
		System.out.println("NPCs:      'talk <npc>'-Displays npc message to user plus gives quest if available");
		Thread.sleep(time);
		System.out.println("           'status <npc>'-Displays your currently known information of an npc");
		Thread.sleep(time);
		System.out.println("           'fight <npc>'-Engage the npc in battle");
		Thread.sleep(time);
		System.out.println("GAMESTATE: 'q'-Exits the game");
		Thread.sleep(time);
		System.out.println("           'qq'- Save and exit the game. Saves to currently opened .sav file, or creates new .sav file if current file is .bork");
		Thread.sleep(time);
		System.out.println("           'save'-Saves to default file name('save1.sav')");
		Thread.sleep(time);
		System.out.println("           'save <fileName>'-Saves to user specified .sav file");
		Thread.sleep(time);
		System.out.println("           'delete <fileName>'-Deletes user specified .sav file");
		Thread.sleep(time);
		} 
		catch(InterruptedException ex) 
		{
		    Thread.currentThread().interrupt();
		}
		return null;
	}
}
