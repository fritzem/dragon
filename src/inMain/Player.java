package inMain;

import java.awt.Graphics2D;

import interfaces.CommandMenu;
import theWorld.State;
import theWorld.World;

public class Player implements focusable, updatable, drawable{
	
	//Movement information
	private static Player instance;
	private int x;
	private int y;
	private double slideX;
	private double slideY;
	private Direction dir;
	private boolean moving;
	private double speed;
	
	private Player()
	{
		speed = 1;
		moving = false;
		x = 10;
		y = 10;
		slideX = 0;
		slideY = 0;
		dir = Direction.DOWN;
		State.setPlayer(this);
		addUpdate();
		instance = this;
	}
	
	public void draw(Graphics2D g)
	{
		SpriteRepo.getSprite("merchant").drawFrame(g, 7 * 16 + 8, 6 * 16 + 8, dir.spriteOff * 2 + ((System.currentTimeMillis() % 550 > 275) ? 1 : 0));
		
	}
	
	public static Player getInstance()
	{
		if (instance == null)
		{
			new Player();
		}
		return instance;
	}
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setLocation(int x, int y, Direction dir)
	{
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public void doneMoving()
	{
		moving = false;
	}
	public int[] getLocation()
	{
		return new int[] {x, y};
	}
	public Direction getDir()
	{
		return dir;
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
	public int getSlideX()
	{
		return (int) slideX;
	}
	public int getSlideY()
	{
		return (int) slideY;
	}
	public void input()
	{
		if (moving)
			return;
		Input input = Input.getInput();
		if (input.getKeys()[4])
		{
			input.getKeys()[4] = false;
			new CommandMenu().execute();
			return;
		}
		for (int i = 0; i < 4; i++)
		{
			if (input.getKeys()[i])
			{
				dir = Direction.parse(i);
				if (State.validPlayerSpot(x+dir.xOff, y+dir.yOff))
				{
					moving = true;
					slideX = 16 * dir.xOff;
					slideY = 16 * dir.yOff;
					x += dir.xOff;
					y += dir.yOff;
					return;
				}
			}
		}
	}
	
	public int[] getFaceBlock()
	{
		return new int[] {x + dir.xOff, y + dir.yOff};
	}

	public boolean update(long delta) {
		slideX();
		slideY();
		if (slideX == 0 && slideY == 0 && moving)
		{
			moving = false;
			World.queryEvent(x, y);
		}
		return false;
	}
}
