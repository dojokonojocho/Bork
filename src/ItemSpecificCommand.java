
class ItemSpecificCommand extends Command{
	private String verb;
	private String noun;
	private Item item;
	ItemSpecificCommand(String verb, String noun){
		this.verb = verb;
		this.noun = noun;
	}
	String execute() {
		GameState gs = GameState.instance();
		item = gs.getItemInVicinityNamed(noun);
		if (item != null) {
			String temp = item.getMessageForString(verb);
			if (temp != null) {
				System.out.println(temp);
			} else {
				System.out.println("You can't "+ verb + " a " + noun);
			}
		} 
		//System.out.println("test");
		return null;
	}
}
