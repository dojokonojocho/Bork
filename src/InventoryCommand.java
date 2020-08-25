
class InventoryCommand extends Command{
	InventoryCommand(){
		
	}
	String execute() {
		GameState gs = GameState.instance();
		System.out.println(gs.showInventory());
		return null;
	}
}
