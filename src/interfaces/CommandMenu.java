package interfaces;

import actions.Talk;

public class CommandMenu extends SelectionMenu{
	
	public CommandMenu() {
		super(96, 8, 128, 80, 2, 4, "COMMAND");
	}

	public void getMenu() {
		list[0][0] = new Talk();
		list[0][1] = new StatusMenu();
		list[1][3] = new TextMenu("just great perfect yes indeed you will be a powerful textbox"
				+ "\\another awaits you, in the dagoah system"
				+ "\\stop messing this up it's easy!!!"
				+ "\\further testing hmm yess we must be sure the textbox is foolproof"
				+ "\\he seems to be handling it... so far so good...");
		list[1][1] = new InventoryMenu();
	}


}
