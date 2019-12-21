package interfaces;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import graphics.Sprite;
import inMain.Input;
import inMain.State;
import inMain.focusable;

public class TextMenu extends FocusMenu{
	
	private static final int rows = 7;
	private static final int cols = 20;
	
	private String text;
	private ArrayList<String> pages;
	private int pageCursor;
	private int charTick;
	
	public TextMenu(String s)
	{
		super(32, 136, 192, 80, "Text");
		text = s;
		showName = false;
		init();
	}
	
	public void init()
	{
		charTick = 0;
		pageCursor = 0;
		pages = new ArrayList<String>(1);
		parse();
	}
	
	//changes text to a format the drawing function can understand
	public void parse()
	{
		String page = "";
		int inline = 0;
		int chline = 0;
		for (int i = 0; i < text.length(); i++)
		{
			//if new page
			if (text.charAt(i) == '\\' || inline >= rows)
			{
				pages.add(page);
				page = "";
				chline = 0;
				inline = 0;
				continue;
			}
			//if newline
			if (text.charAt(i) == '|')
			{
				page += '|';
				inline++;
				chline = 0;
				continue;
			}
			
			//if space
			if (text.charAt(i) == ' ') 
			{
				page += " ";
				chline++;
				continue;
			}
			
			//iterates through a word, checking for run off
			for (int k = i; k < text.length() && text.charAt(k) != ' ' && text.charAt(k) != '\\'; k++)
			{
				//finds run off line
				if (chline + (k-i+1) > cols)
				{
					if (++inline >= rows)
					{
						pages.add(page);
						page = "";
						i--;
						chline = 0;
						inline = 0;
						break;
					}
					page += '|';
					chline = 0;
					break;
				}
				
			}
			
			//prints word
			for (int k = i; k < text.length() && text.charAt(k) != ' ' && text.charAt(k) != '\\'; k++)
			{
				page += text.charAt(k);
				i++;
				chline++;
			}
			i--;

		}
		pages.add(page);
	}
	
	
	public void draw(Graphics2D g, Sprite[] chars)
	{
		super.draw(g, chars);
		String[] thins = (pages.get(pageCursor)).split("\\|");
		int xi = x + 8;
		int yi = y + 8;
		int lengths = 0;
		for (String s : thins)
		{
			if (charTick > lengths + s.length())
				drawText(g, chars, s, xi, yi);
			else
				drawText(g, chars, s.substring(0, charTick - lengths), xi, yi);
			lengths += s.length();
			if (lengths > charTick)
				break;
			yi += 8;
		}
		
	}
	
	public boolean execute()
	{
		init();
		return super.execute();
	}

	public void input() 
	{
		
		if (Input.getInput().getKeys()[4])
		{
			Input.getInput().getKeys()[4] = false;
			if (++pageCursor == pages.size())
			{	
				pageCursor = 0;
				close();
			}
			
		}
	}
	
	public boolean update(long delta)
	{
		if (open)
			charTick++;
		return super.update(delta);
	}
	
	public String getText()
	{
		return text;
	}
}
