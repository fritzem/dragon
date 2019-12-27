package interfaces;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import java.util.Iterator;

import graphics.Sprite;
import inMain.Input;
import inMain.State;
import inMain.focusable;

public class TextMenu extends FocusMenu{
	
	private static final int rows = 7;
	private static final int cols = 23;
	
	private String text;
	private ArrayList<String> entries;
	private int pC;
	private int head = 0;
	
	private int charTick;
	private boolean scrolling;
	
	public TextMenu(String s)
	{
		super(32, 136, 192, 80, "Text");
		text = s;
		showName = false;
	}
	
	public void init()
	{
		head = 0;
		charTick = 0;
		pC = 0;
		scrolling = true;
		entries = new ArrayList<String>(1);
		parse();
	}
	
	//changes text to a format the drawing function can understand
	public void parse()
	{
		String entry = "";
		int chline = 0;
		for (int i = 0; i < text.length(); i++)
		{
			//if new page
			if (text.charAt(i) == '\\')
			{
				entries.add(entry.trim());
				entries.add("\\");
				entry = "";
				chline = 0;
				continue;
			}
			//if newline
			if (text.charAt(i) == '|')
			{
				entries.add(entry.trim());
				entry = "";
				chline = 0;
				continue;
			}
			
			//if space
			if (text.charAt(i) == ' ') 
			{
				entry += " ";
				chline++;
				continue;
			}
			
			//iterates through a word, checking for run off
			boolean runoff = false;
			for (int k = i; k < text.length() && text.charAt(k) != ' ' && text.charAt(k) != '\\' && text.charAt(k) != '|'; k++)
			{
				//finds run off line
				if (chline + (k-i+1) >= cols)
				{
					entries.add(entry.trim());
					entry = "";
					chline = 0;
					runoff = true;
					break;
				}
			}
			if (runoff)
			{
				i--;
				continue;
			}
				
			
			//prints word
			for (int k = i; k < text.length() && text.charAt(k) != '|' && text.charAt(k) != ' ' && text.charAt(k) != '\\'; k++)
			{
				entry += text.charAt(k);
				i++;
				chline++;
			}
			i--;

		}
		entries.add(entry.trim());
		/*
		for (String s : entries)
		{
			System.out.println(s + 8);
		} */
	}
	
	
	public void draw(Graphics2D g, Sprite[] chars)
	{
		super.draw(g, chars);
		int xi = x + 8;
		int yi = y + 8;
		int lengths = 0;
		int inline = -1;
		for (pC = head; pC <= entries.size() && lengths <= charTick; pC++)
		{
			String s;
			if (pC == entries.size())
			{
				s = " ";
				scrolling = false;
			}
			else
				s = entries.get(pC);
			if (s.compareTo("") == 0)
				continue;
			if (++inline > rows)
			{
				charTick -= entries.get(head).length();
				lengths -= entries.get(head).length();
				entries.set(head++, "");
			}
			//System.out.println(s);
			if (s.compareTo("\\") == 0)
			{
				drawText(g, chars, "|", xi, yi + inline * 8);
				scrolling = false;
				break;
			}
			if (charTick > lengths + s.length())
				drawText(g, chars, s, xi, yi + inline * 8);
			else
				drawText(g, chars, s.substring(0, charTick - lengths), xi, yi + inline * 8);
			lengths += s.length();
			
		}
		/*
		drawText(g, chars, Integer.toString(pC) + " " + Integer.toString(lengths)
				+ " " + Integer.toString(charTick) + " " + Integer.toString(head), xi, yi - 8);
		for (String s : entries)
			System.out.print("=" + s);
		System.out.println(); */
	}
	
	public boolean execute()
	{
		init();
		return super.execute();
	}

	public void input() 
	{
		if (Input.getInput().getKeys()[4] && !scrolling)
		{
			Input.getInput().getKeys()[4] = false;
			if (pC >= entries.size())
			{	
				pC = 0;
				close();
			}
			else
			{
				entries.set(pC, " ");
				scrolling = true;
			}
		}
	}
	
	public boolean update(long delta)
	{
		if (open && scrolling)
			charTick++;
		return super.update(delta);
	}
	
	public String getText()
	{
		return text;
	}
}
