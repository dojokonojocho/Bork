
class UnknownCommand extends Command{
	private String unknown;
	
	UnknownCommand(String unknown){
		this.unknown = unknown;
	}
	String execute() {
		System.out.println("The command '" + unknown + "' is not valid");
		return null;
	}
}
