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
		menu[1][3] = new TextMenu("testing text");
		return menu;
	}


}
