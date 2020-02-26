package interfaces;

import java.awt.Graphics;
import java.awt.Graphics2D;

import graphics.Sprite;
import inMain.Input;
import theWorld.State;

public abstract class SelectionMenu extends FocusMenu{
	
	protected MenuItem[][] list;
	private int listSizeX;
	private int listSizeY;
	private int cursor;
	
	//How far from the top the list begins
	protected int yOffset;
	
	
	public SelectionMenu(int x, int y, int width, int height, int listSizeX, int listSizeY, String name) {
		super(x, y, width, height, name);
		this.listSizeX = listSizeX;
		this.listSizeY = listSizeY;
		yOffset = 16;
		cursor = 0;
		initMenu();
		getMenu();
	}
	
	//Initializes entries with null values
	public void initMenu()
	{
		MenuItem[][] menu = new MenuItem[listSizeX][listSizeY];
		
		for (int i = 0; i < menu.length; i++)
		{
			for (int j = 0; j < menu[0].length; j++)
			{
				menu[i][j] = new NullItem();
			}
		}
		list = menu;
	}
	
	//Implementations of the selection menu initialize their selections with this method
	public abstract void getMenu();
	
	public void draw(Graphics2D g, Sprite[] chars)
	{
		super.draw(g, chars);
		for (int i = 0; i < list.length; i++)
		{
			for (int j = 0; j < list[0].length; j++)
			{
				drawText(g, chars, list[i][j].getName(), i * 64 + x + 16, j * 16 + y + yOffset);
			}
		}
		
		chars[91].draw(g, x + 8 + cursor / list[0].length * 64, y + yOffset + (cursor % list[0].length) * 16, cap);
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
