package interfaces;

import java.awt.Graphics;
import java.awt.Graphics2D;

import graphics.Sprite;
import inMain.Input;
import theWorld.State;

public abstract class SelectionMenu extends FocusMenu{
	
	private Menu[][] list;
	private int cursor;
	
	
	public SelectionMenu(int x, int y, int width, int height, String name) {
		super(x, y, width, height, name);
		cursor = 0;
		this.list = getMenu();
		
	}
	
	public abstract Menu[][] getMenu();
	
	public void draw(Graphics2D g, Sprite[] chars)
	{
		super.draw(g, chars);
		for (int i = 0; i < list.length; i++)
		{
			for (int j = 0; j < list[0].length; j++)
			{
				drawText(g, chars, list[i][j].name, i * 64 + x + 16, j * 16 + y + 16);
			}
		}
		
		chars[91].draw(g, x + 8 + cursor / list[0].length * 64, y + 16 + (cursor % list[0].length) * 16, cap);
	}

	public void input()
	{
		if (!open)
			return;
		Input input = Input.getInput();
		if (input.getKeys()[0])
		{
			input.getKeys()[0] = false;			
			if (cursor % list[0].length > 0)
				cursor--;
		}
		if (input.getKeys()[1])
		{
			input.getKeys()[1] = false;
			if (cursor >= list[0].length)
				cursor -= list[0].length;
		}
		if (input.getKeys()[2])
		{
			input.getKeys()[2] = false;			
			if (cursor % list[0].length < list[0].length - 1)
				cursor++;
		}
		if (input.getKeys()[3])
		{
			input.getKeys()[3] = false;			
			if (cursor / list[0].length < list.length - 1)
				cursor += list[0].length;
		}
		if (input.getKeys()[4])
		{
			input.getKeys()[4] = false;		
			
			if (list[cursor / list[0].length][cursor % list[0].length].execute())
			{
				close();
			}
		}
		if (input.getKeys()[5])
		{
			input.getKeys()[5] = false;
			close();
		}
	}

}
