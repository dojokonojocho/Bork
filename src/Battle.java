import java.util.*;
public class Battle {
	private ArrayList<NPC> enemies = new ArrayList<NPC>();
	GameState gs = GameState.instance();
	private int health;
	private int stamina;
	private int MAX_STAM;
	public Battle(NPC...npcs) {
		for (NPC npc:npcs) {
			enemies.add(npc);
		}
		health = gs.getHealth();
		stamina = gs.getStamina();
		MAX_STAM = stamina;
	}
	public void fight() {
		System.out.println();
		for (NPC enemy: enemies) {
			if (enemy.getCry() != null) {
				System.out.println(enemy.getCry());
			}
		}
		displayCurrentStatus();
		System.out.println("Type 'help' for a list of combat commands");
		Scanner in = new Scanner(System.in);
		boolean swtch = true;
		while (swtch) {
			System.out.print(":");
			String input = in.nextLine();
			if (input.startsWith("attack")) {
				attack(input);
			} else if((input.startsWith("a")) && (input.length()==3)) {
				String attackString = "attack ";
				attackString += enemies.get(0).getName() + " ";
				if (input.charAt(2) == 'p') {
					try {
						attackString += gs.getPrimaryWeapon().getPrimaryName();
						attack(attackString);
					} catch (Exception e) {
						System.out.println("You dont have a primary weapon equiped. Try using your seconday weapon or remove 'p' to attack with your fists");
					}
				}
			} else if((input.startsWith("a")) && (input.length()==2)) {
				String attackString = "attack ";
				attackString += enemies.get(0).getName() + " ";
				attack(attackString);
			} else if (input.equals("flee")) {
				swtch = false;
				System.out.println("You have fled the battle");
				System.out.println(gs.getAdventurersCurrentRoom().describe());
			} else if (input.equals("help")) {
				help();
			} else if (input.equals("rest")) {
				rest();
			} else {
				System.out.println(input + " is not a valid combat command. Type 'help' for a list of valid combat commands.");
			}
			if (enemies.size() < 1) {
				swtch = false;
				System.out.println(gs.getAdventurersCurrentRoom().describe());
			}
		}
		
	}
	public void displayCurrentStatus() {
		GameState gs = GameState.instance();
		System.out.printf("You %28s (1)\n", enemies.get(0).getName());
		System.out.printf("Health: %-18d Health: %d\n", health, enemies.get(0).getHealth());
		System.out.printf("Stamina: %-17d\n", stamina);
	}
	public String npcAction(NPC enemy){
		Random r = new Random();
		int action = r.nextInt(3);
		if (action == 0) {
			if (enemy.getStamina() > 0) {
				return "Attack";
			} else {
				return npcAction(enemy);
			}
		} else if(action == 1) {
			return "Rest";
		}
		return "Block";
	}
	public void attack(String s) {
		String[] attackArray = s.split(" ");
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getName().equals(attackArray[1])) {
				if (attackArray.length == 3) {
					Item temp = gs.getEquipedItem(attackArray[2]);
					if (temp != null) {
						if (stamina >= temp.getStamina()) {
							enemies.get(i).changeHealth(temp.getDamage());
							stamina -= temp.getStamina();
							if (temp.getCombatType().equals("melee")) {
								System.out.println("You swing your " + temp.getPrimaryName() + " and hit " + attackArray[1] + " dealing " + temp.getDamage() + " damage.");
							}
							if (enemies.get(i).getHealth() <= 0) {
								removeEnemy(enemies.get(i));
							}
							if (enemies.size() > 0) {
								displayCurrentStatus();
							}
						} else {
							System.out.println("You are too tired to preform this attack.");
						}
					} else {
						System.out.println("You do not have a " + attackArray[2] + ".");
					}
				} else {
					if (stamina >= 1) {
						stamina--;
						enemies.get(i).changeHealth(gs.getDungeon().items.get("fist").getDamage());
						System.out.println("You punched " + enemies.get(i).getName() + " dealing " + gs.getDungeon().items.get("fist").getDamage() + " damage.");
						if (enemies.get(i).getHealth() <= 0) {
							removeEnemy(enemies.get(i));
						}
						if (enemies.size() > 0) {
							displayCurrentStatus();
						}
					} else {
						System.out.println("You are too tired to even attempt a punch.");
					}
				}
			}
		}
	}
	public void rest() {
		if (stamina == MAX_STAM) {
			System.out.println("You are already at your peek strength");
		} else {
			System.out.println("You feel your strength regaining.");
		}
		stamina += 4;
		if (stamina > MAX_STAM) {
			stamina = MAX_STAM;
		}
		displayCurrentStatus();
	}	
	public void help() {
		System.out.println("'attack <npc> <weapon>'-Attemps to attack an npc with a weapon. Leave <weapon> empty to attack with your fists");
		System.out.println("'a<npcNum><p/s>'-Shortcut for attack, an 'a' followed by the index of the npc followed by a 'p' or 's' to denote\n    either your primary or secondary weapon. All with no spaces. e.g. 'a1p'");
		System.out.println("'flee'-Atempt to escape the fight");
		System.out.println("'rest'-Regain some stamina. However you are defenseless");
	}
	public void removeEnemy(NPC enemy){
		String dieString = enemy.getName() + " has been vanquished";
		if (enemy.getInventory() != null) {
			dieString += ", and has dropped a ";
			for (int j = 0; j < enemy.getInventory().size(); j++) {
				if (j > 0) {
					dieString += ", ";
				}
				dieString += enemy.getInventory().get(j).getPrimaryName();
				gs.getAdventurersCurrentRoom().addItem(enemy.getInventory().get(j));
			}
		}
		System.out.println(dieString + ".");
		gs.getAdventurersCurrentRoom().removeNPC(enemy);
		enemies.remove(enemy);
	}
}
