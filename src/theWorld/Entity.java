package theWorld;

import java.awt.Graphics2D;
import java.util.Random;

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
	private double slideX;
	private double slideY;
	private boolean moving;
	private double speed;
	
	private Direction dir;
	private Behavior behaves;
	
	private ISprite sprite;
	private IMenu talk;
	
	private long deltaCounter;
	
	public Entity(int x, int y, Direction dir, ISprite sprite, IMenu talk, Behavior behaves)
	{
		this.x = x;
		this.y = y;
		this.moving = false;
		this.speed = 1;
		
		this.dir = dir;
		this.behaves = behaves;
		
		this.sprite = sprite;
		this.talk = talk;
		
		this.deltaCounter = 0l;
		addUpdate();
	}
	
	public void draw(Graphics2D g) {
		Player p = Player.getInstance();
		int[] loc = p.getLocation();
		sprite.drawFrame(g, (int)((x - loc[0] + 7) * 16 + 8 + p.getSlideX() + slideX), 
							(int)((y - loc[1] + 6) * 16 + 8 + p.getSlideY() + slideY), 
							dir.spriteOff * 2 + ((System.currentTimeMillis() % 550 > 275) ? 1 : 0));
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

	public boolean update(long delta) {
		slideX();
		slideY();
		if (moving && slideX == 0 && slideY == 0)
			moving = false;
		if (behaves == Behavior.STILL)
			return false;
		if (!moving)
			deltaCounter += delta;
		if (deltaCounter >= 1500000000l)
		{
			deltaCounter = 0;
			randomMove();
		}
		return false;
	}
	
	public void randomMove() {
		this.dir = Direction.randDirection();
		move(this.dir);
	}
	
	public void move(Direction dir) {
		if (State.validSpot(x + dir.xOff, y + dir.yOff))
		{
			moving = true;
			slideX = dir.xOff * -16;
			slideY = dir.yOff * -16;
			x += dir.xOff;
			y += dir.yOff;
		}
	}
	
	public int slideX()
	{
		if (slideX > 0)
		{
			slideX -= speed;
			if (slideX < 0)
				slideX = 0;
			return (int) slideX;
		}
			
		if (slideX < 0)
		{
			slideX += speed;
			if (slideX > 0)
				slideX = 0;
			return (int) slideX;
		}
		return 0;
	}
	public int slideY()
	{
		if (slideY > 0)
		{
			slideY -= speed;
			if (slideY < 0)
				slideY = 0;
			return (int) slideY;
			
		}
			
		if (slideY < 0)
		{
			slideY += speed;
			if (slideY > 0)
				slideY = 0;
			return (int) slideY;
		}
		return 0;
	}
}
