
public class UnequipCommand extends Command{
	private Item unequipItem;
	GameState gs = GameState.instance();
	public UnequipCommand(String s) {
		String[] itemArray = s.split(" ");
		unequipItem = gs.getEquipedItem(itemArray[1]);
	}
	String execute() {
		if (unequipItem != null) {
			gs.unequipItem(unequipItem);
			System.out.println("You have unequiped " + unequipItem.getPrimaryName());
			gs.hasSaved = false;
		} else {
			System.out.println("You do not have " + unequipItem.getPrimaryName() + " equiped.");
		}
		return null;
	}

}
