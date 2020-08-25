
class NPCSpecificCommand extends Command{
	private String verb;
	private String noun;
	private NPC npc;
	NPCSpecificCommand(String verb, String noun){
		this.verb = verb;
		this.noun = noun;
		GameState gs = GameState.instance();
		npc = gs.getNPCInRoomNamed(noun);
	}
	
	String execute() {
		GameState gs = GameState.instance();
		if (npc != null) {
			if (verb.equals("talk")) {
				System.out.println(npc.getMessage());
				gs.hasSaved = false;
			} else if (verb.equals("status")) {
				System.out.println(npc.getStatus());
				gs.hasSaved = false;
			} else if (verb.equals("fight")) {
				NPC enemy = gs.getNPCInRoomNamed(noun);
				Battle battle = new Battle(enemy);
				battle.fight();
				gs.hasSaved = false;
			} else {
				System.out.println(noun + " looks at you puzzled, not knowing what '" + verb + "' means");
			}
		} else {
			System.out.println("The command '" + verb + " " + noun + "' is invalid");
		}
		return null;
	}

}
