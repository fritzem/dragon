package theWorld;

import java.awt.Graphics2D;

import graphics.ISprite;
import graphics.PaletteSprite;
import inMain.Direction;
import inMain.Player;
import inMain.SpriteRepo;
import inMain.drawable;
import interfaces.TextMenu;

public class Entity implements drawable {
	private int x;
	private int y;
	private Direction dir;
	
	private boolean move;
	
	private PaletteSprite sprite;
	
	
	private TextMenu talk;
	
	public Entity(int x, int y, Direction dir, PaletteSprite sprite, TextMenu talk)
	{
		this.x = x;
		this.y = y;
		this.dir = dir;
		
		this.sprite = sprite;
		this.talk = talk;
		
	}
	
	public void draw(Graphics2D g, int x, int y)
	{
		sprite.draw(g, x, y);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void talk()
	{
		if (talk != null)
			talk.execute();
		else
			new TextMenu("There is no one there.").execute();
	}

	public void draw(Graphics2D g) {
		Player p = Player.getInstance();
		int[] loc = p.getLocation();
		sprite.drawFrame(g, (x - loc[0] + 7) * 16 + 8 + p.getSlideX(), 
				(y - loc[1] + 6) * 16 + 8 + p.getSlideY(), 
				dir.spriteOff * 2 + ((System.currentTimeMillis() % 550 > 275) ? 1 : 0));
	}
}
