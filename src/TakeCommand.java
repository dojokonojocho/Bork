
class TakeCommand extends Command{
	private String itemName;
	private Item takenItem;
	TakeCommand(String name) {
		try {
			String[] command = name.split(" ");
			this.itemName = command[1];
		} catch (IndexOutOfBoundsException e){
			System.out.println("I don't know what to take");
		}
	}
	String execute() {
		if (itemName != null) {
			GameState gs = GameState.instance();
			takenItem = gs.getAdventurersCurrentRoom().getItemNamed(itemName);
			if (takenItem != null) {
				gs.addToInventory(takenItem);
				gs.getAdventurersCurrentRoom().remove(takenItem);
				System.out.println("You pick up a " + itemName);
				gs.hasSaved = false;
			}
		}
		return null;
	}
}
