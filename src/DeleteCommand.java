import java.io.*;
import java.util.*;
class DeleteCommand extends Command{
	private String saveFilename;
	private Scanner in = new Scanner(System.in);
	
	DeleteCommand(String f){
		if (f.startsWith("delete ")){
			try {
				String[] temp = f.split(" ");
				if (temp[1].substring(temp[1].lastIndexOf(".")+1).equals("sav")) {
					this.saveFilename = temp[1];
				} else {
					System.err.println("Filename does not contain .sav extension");
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid syntax for 'delete' command");
				this.saveFilename = null;
			}
		} else {
			System.out.println("I don't know what to delete");
			this.saveFilename = null;
		}
	}
	String execute() {
		if (saveFilename != null) {
			System.out.println("ARE YOU SURE YOU WANT TO DELETE " + saveFilename + "? THIS WILL BE PERMANENT. (y/n)");
			String yesNo = in.next();
			if (yesNo.equals("y")) {
				File file = new File(saveFilename);
		        if(file.delete())
		        {
		            System.out.println(saveFilename + " deleted successfully");
		        } else {
		            System.out.println("Failed to delete " + saveFilename);
		        }
			}
		}
		return null;
	} 
}
