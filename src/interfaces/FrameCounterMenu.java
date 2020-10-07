package interfaces;

import java.awt.Graphics2D;

import graphics.ISprite;
import inMain.Game;
import theWorld.State;

public class FrameCounterMenu extends Menu {

	public FrameCounterMenu() {
		super(0, 0, 32, 24, "FPS");
		showName = false;
	}
	
	
	public void draw(Graphics2D g, ISprite[] chars)
	{
		super.draw(g, chars);
		
		drawText(g, chars, Integer.toString(Game.frames), x + 8, y + 8);
	}

}
