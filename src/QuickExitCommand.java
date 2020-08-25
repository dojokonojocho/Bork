
class QuickExitCommand extends Command{
	private String fileName;
	QuickExitCommand(){
		GameState gs = GameState.instance();
		this.fileName = gs.getInitFileName();
	}
	String execute() {
		
		if (fileName.substring(fileName.lastIndexOf(".")+1).equals("sav")) {
			Command quickSave = new SaveCommand("save " + fileName);
			quickSave.execute();		
		} else if (fileName.substring(fileName.lastIndexOf(".")+1).equals("bork")) {
			String temp = fileName.substring(0, fileName.indexOf("."));
			Command quickSave = new SaveCommand("save " + temp + ".sav");
			quickSave.execute();
		}
		System.out.println("Terminating bork engine");
		System.exit(0);
		return null;
	}

}
