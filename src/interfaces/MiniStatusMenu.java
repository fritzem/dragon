package interfaces;

import java.awt.Graphics;
import java.awt.Graphics2D;

import graphics.Sprite;

public class MiniStatusMenu extends Menu {

	public MiniStatusMenu() {
		super(16, 24, 64, 96, "Bill");
	}
	

	public void input() {
		
	}
	
	public void draw(Graphics2D g, Sprite[] chars)
	{
		super.draw(g, chars);
	}
}
