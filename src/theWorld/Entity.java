package theWorld;

import java.awt.Graphics2D;

import graphics.ISprite;
import graphics.PaletteSprite;
import inMain.Direction;
import inMain.Player;
import inMain.SpriteRepo;
import inMain.drawable;
import inMain.updatable;
import interfaces.IMenu;
import interfaces.TextMenu;

public class Entity implements drawable, updatable {
	private int x;
	private int y;
	private Direction dir;
	private Behavior behaves;
	
	private PaletteSprite sprite;
	private IMenu talk;
	
	private long deltaCounter;
	
	public Entity(int x, int y, Direction dir, PaletteSprite sprite, IMenu talk, Behavior behaves)
	{
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.behaves = behaves;
		
		this.sprite = sprite;
		this.talk = talk;
		
		this.deltaCounter = 0l;
		addUpdate();
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
		this.dir = Direction.inverse(Player.getInstance().getDir());
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

	public boolean update(long delta) {
		if (behaves == Behavior.STILL)
			return false;
		
		return false;
	}
}
