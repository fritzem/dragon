package scripts;

import inMain.Player;
import theWorld.World;

public class WarpScript extends Script{
	
	private String dest;
	private int destX;
	private int destY;
	
	public WarpScript(String dest, int destX, int destY)
	{
		super();
		this.dest = dest;
		this.destX = destX;
		this.destY = destY;
	}
	public boolean update(long delta) {
		ticks++;
		switch(ticks)
		{
		case 15:
			break;
		case 30:
			break;
		case 45:
			break;
		case 60:
			break;
		case 61:
			World.setMap(dest);
			Player.getInstance().setLocation(destX,destY);
			break;
		case 75:
			break;
		case 90:
			break;
		case 105:
			clearFocus();
			return true;
		default:
			return false;
		}
		return false;
	}

}
