package items;

import interfaces.TextMenu;

public class Torch extends Item {
	
	public Torch()
	{
		super("Torch");
	}
	
	public boolean execute()
	{
		new TextMenu("A torch can be used only in dark places.").execute();
		return false;
	}
}
