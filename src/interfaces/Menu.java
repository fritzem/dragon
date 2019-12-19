package interfaces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import graphics.Sprite;
import inMain.Input;
import inMain.State;
import inMain.focusable;
import inMain.updatable;

public abstract class Menu implements updatable{
	
	protected int ticks;
	protected int cap;
	protected boolean open;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected String name;
	
	protected boolean showName;
	
	public Menu(int x, int y, int width, int height, String name)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		
		
		showName = true;
		open = false;
	}

	public void draw(Graphics2D g, Sprite[] chars)
	{
		g.setColor(Color.BLACK);
		
		g.fillRect(x, y, width, cap - y);
		for (int i = x + 8; i < x + width - 8; i += 8)
		{
			chars[114].draw(g, i, y, cap);
			chars[120].draw(g, i, y + height - 8, cap);
		}
		for (int i = y + 8; i < y + height - 8; i += 8)
		{
			chars[113].draw((g), x, i, cap);
			chars[118].draw(g, x + width - 8, i, cap);
		}
		chars[116].draw(g, x, y, cap);
		chars[119].draw(g, x + width - 8, y, cap);
		chars[117].draw(g, x, y + height - 8, cap);
		chars[121].draw(g, x + width - 8, y + height - 8, cap);
		if (showName)
			drawTitle(g, chars);
	}
	
	public void drawText(Graphics2D g, Sprite[] chars, String text, int x, int y)
	{
		
		for (int i = 0; i < text.length(); i++)
		{
			chars[text.charAt(i) - 32].draw(g, x + i * 8, y, cap);
		}
	}
	
	public void drawTitle(Graphics2D g, Sprite[] chars)
	{
		int mid = (int) (((double)width / 2) - ((name.length()) / 2) * 8);
		drawText(g, chars, name, x + mid, y);
	}
	
	public boolean execute()
	{
		cap = y;
		ticks = 0;
		open = false;
		State.enqueue(this);
		addUpdate();
		return true;
	}
	
	public void close()
	{
		cap = 0;
		ticks = 0;
		open = false;
		State.dequeue(this);
		removeUpdate();
	}
	
	public void update(long delta)
	{
		if (!open)
		{
			ticks++;
			cap = y + ticks / 1 * 8;
			if (cap >= y + height)
			{
				cap = y + height;
				open = true;
			}
		}
	}
	
	
}
