package theWorld;

import inMain.drawable;

public class Entity {
	private int x;
	private int y;
	private boolean move;
	private int dir;
	
	private int sprite;
	
	public Entity(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
