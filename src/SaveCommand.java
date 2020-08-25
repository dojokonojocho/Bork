
class SaveCommand extends Command{
	private String saveFilename;
	
	SaveCommand(String f){
		if (f.startsWith("save ")){
			try {
				String[] temp = f.split(" ");
				if (temp[1].substring(temp[1].lastIndexOf(".")+1).equals("sav")) {
					this.saveFilename = temp[1];
				} else {
					System.err.println("Filename does not contain .sav extension");
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid syntax for 'save' command");
				this.saveFilename = null;
			}
		} else {
			GameState gs = GameState.instance();
			this.saveFilename = gs.getInitFileName();
			if (saveFilename.substring(saveFilename.lastIndexOf(".")+1).equals("bork")) {
				String temp = saveFilename.substring(0, saveFilename.indexOf("."));
				saveFilename = temp + ".sav";
			}
		}
	}
	String execute() {
		if (saveFilename != null) {
			GameState gs = GameState.instance();
			gs.store(saveFilename);
			gs.hasSaved = true;
		}
		return null;
	} 
}
