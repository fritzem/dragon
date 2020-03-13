package theWorld;

import inMain.drawable;
import interfaces.TextMenu;

public class Entity {
	private int x;
	private int y;
	private boolean move;
	private int dir;
	
	private int sprite;
	private TextMenu talk;
	
	public Entity(int x, int y, TextMenu talk)
	{
		this.x = x;
		this.y = y;
		this.talk = talk;
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
}
