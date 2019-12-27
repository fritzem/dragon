package interfaces;

public class CommandMenu extends SelectionMenu{
	
	public CommandMenu() {
		super(96, 8, 128, 80, "COMMAND");
	}

	public Menu[][] getMenu() {
		Menu[][] menu = new Menu[2][4];
		
		for (int i = 0; i < menu.length; i++)
		{
			for (int j = 0; j < menu[0].length; j++)
			{
				menu[i][j] = new NullItem();
			}
		}
		
		menu[0][1] = new StatusMenu();
		menu[1][3] = new TextMenu("just great perfect yes indeed you will be a powerful textbox"
				+ "\\another awaits you, in the dagoah system"
				+ "\\stop messing this up it's easy!!!"
				+ "\\further testing hmm yess we must be sure the textbox is foolproof"
				+ "\\he seems to be handling it... so far so good...");
		return menu;
	}


}
