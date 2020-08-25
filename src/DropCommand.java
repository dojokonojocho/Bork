
class DropCommand extends Command{
	private String itemName;
	private Item dropItem;
	
	DropCommand(String name){
		try {
			String[] command = name.split(" ");
			this.itemName = command[1];
		} catch (IndexOutOfBoundsException e){
			System.out.println("I don't know what to drop");
		}
	}
	String execute() {
		if (itemName != null) {
			GameState gs = GameState.instance();
			dropItem = gs.getItemInInventoryNamed(itemName);
			if (dropItem != null) {
				gs.getAdventurersCurrentRoom().addItem(dropItem);
				gs.removeFromInventory(dropItem);
				System.out.println("You dropped " + dropItem.getPrimaryName());
				gs.hasSaved = false;
			}
		}
		return null;
	}


}
