package interfaces;

import java.awt.Graphics2D;

import graphics.ISprite;
import theWorld.State;

public class StatusMenu extends FocusMenu{
	
	public StatusMenu() {
		super(80, 40, 160, 176, "STATUS");
		showName = false;
	}
	
	
	public void draw(Graphics2D g, ISprite[] chars)
	{
		super.draw(g, chars);
		drawTextRight(g, chars, "NAME:", x + 10 * 8, y + 8);
		drawTextRight(g, chars, "STRENGTH:", x + 15 * 8, y + 24);
		drawTextRight(g, chars, Integer.toString(5), x + 18 * 8, y + 24);
		drawTextRight(g, chars, "AGILITY:", x + 15 * 8, y + 40);
		drawTextRight(g, chars, "MAXIMUM HP:", x + 15 * 8, y + 56);
		drawTextRight(g, chars, "MAXIMUM MP:", x + 15 * 8, y + 72);
		drawTextRight(g, chars, "ATTACK POWER:", x + 15 * 8, y + 88);
		drawTextRight(g, chars, "DEFENSE POWER:", x + 15 * 8, y + 104);
		drawTextRight(g, chars, "WEAPON:", x + 9 * 8, y + 120);
		drawTextRight(g, chars, "ARMOR:", x + 9 * 8, y + 136);
		drawTextRight(g, chars, "SHIELD:", x + 9 * 8, y + 152);
	}

}
