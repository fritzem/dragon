package interfaces;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Sprite;
import inMain.Input;
import inMain.focusable;
import theWorld.State;

public abstract class FocusMenu extends Menu implements focusable {
	
	public FocusMenu(int x, int y, int width, int height, String name)
	{
		super(x, y, width, height, name);
	}

	
	public boolean execute()
	{
		aug = 1;
		cap = y;
		ticks = 0;
		open = false;
		State.enqueue(this);
		pushUpdate();
		addFocus();
		return false;
	}
	
	public void close()
	{
		super.close();
		clearFocus();
	}
	
	public void input()
	{
		if (!open)
			return;
		if (Input.getInput().getKeys()[4])
		{
			Input.getInput().getKeys()[4] = false;			
			close();
		}
		if (Input.getInput().getKeys()[5])
		{
			Input.getInput().getKeys()[5] = false;
			close();
		}
	}
	
	
}
