package scripts;

import graphics.Display;
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
		case 7:
			Display.fade = 1;
			break;
		case 11:
			Display.fade = 2;
			break;
		case 15:
			Display.fade = 3;
			break;
		case 19:
			Display.fade = 4;
			break;
		case 35:
			World.setMap(dest);
			Player.getInstance().setLocation(destX,destY);
			break;
		case 60:
			Display.fade = 3;
			break;
		case 64:
			Display.fade = 2;
			break;
		case 68:
			Display.fade = 1;
			break;
		case 72:
			Display.fade = 0;
			clearFocus();
			return true;
		default:
			return false;
		}
		return false;
	}

}
