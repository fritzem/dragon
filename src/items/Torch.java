package items;

import interfaces.TextMenu;

public class Torch extends Item {
	
	public Torch()
	{
		super("Torch");
	}
	
	public void function()
	{
		new TextMenu("A torch can be used only in dark places.").execute();
	}
}
