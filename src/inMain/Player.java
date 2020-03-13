package inMain;

import interfaces.CommandMenu;
import theWorld.State;
import theWorld.World;

public class Player implements focusable, updatable{
	
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
		if (input.getKeys()[0])
		{
			dir = Direction.UP;
			if (State.validSpot(x, y-1))
			{
				moving = true;
				slideY = -16;
				y--;
			}
			
		}
		else if (Input.getInput().getKeys()[1])
		{
			dir = Direction.LEFT;
			if (State.validSpot(x-1, y))
			{
				moving = true;
				slideX = -16;
				x--;
			}
			
		}
		else if (Input.getInput().getKeys()[2])
		{
			dir = Direction.DOWN;
			if (State.validSpot(x, y+1))
			{
				moving = true;
				slideY = 16;
				y++;
			}
			
		}
		else if (Input.getInput().getKeys()[3])
		{
			dir = Direction.RIGHT;
			//if valid move
			if (State.validSpot(x+1,y))
			{
				moving = true;
				slideX = 16;
				x++;
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
		if (slideX == 0 && slideY == 0)
			World.queryEvent(x, y);
		return false;
	}
}
