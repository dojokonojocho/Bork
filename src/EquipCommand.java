
public class EquipCommand extends Command{
	private Item equipment;
	GameState gs = GameState.instance();
	public EquipCommand(String s) {
		String[] equipmentArray = s.split(" ");
		this.equipment = gs.getItemInInventoryNamed(equipmentArray[1]);
	}

	public String execute() {
		if (equipment != null) {
			if (equipment.getCombatType() != null) {
				gs.eqiupItem(equipment);
				gs.hasSaved = false;
			} else {
				System.out.println("You can not use a " + equipment.getPrimaryName() + " for comabat");
			}
		}
		return null;
	}
}
