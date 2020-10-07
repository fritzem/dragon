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
		System.out.println("WarpScript running");
		this.dest = dest;
		this.destX = destX;
		this.destY = destY;
	}
	public boolean update(long delta) {
		ticks++;
		switch(ticks)
		{
		case 8:
			Display.fade = 1;
			break;
		case 16:
			Display.fade = 2;
			break;
		case 24:
			Display.fade = 3;
			break;
		case 32:
			Display.fade = 4;
			break;
		case 35:
			World.setMap(dest);
			Player.getInstance().setLocation(destX,destY);
			break;
		case 60:
			Display.fade = 3;
			break;
		case 68:
			Display.fade = 2;
			break;
		case 76:
			Display.fade = 1;
			break;
		case 84:
			Display.fade = 0;
			clearFocus();
			return true;
		default:
			return false;
		}
		return false;
	}

}
