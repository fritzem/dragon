package interfaces;

import java.awt.Graphics2D;

import graphics.Sprite;
import inMain.State;

public class FrameCounterMenu extends Menu {

	public FrameCounterMenu() {
		super(0, 0, 32, 24, "FPS");
		showName = false;
	}
	
	
	public void draw(Graphics2D g, Sprite[] chars)
	{
		super.draw(g, chars);
		
		drawText(g, chars, Integer.toString(State.getFPS()), x + 8, y + 8);
	}

}
