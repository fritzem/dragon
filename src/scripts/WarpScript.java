package scripts;

import graphics.Display;
import inMain.Direction;
import inMain.Player;
import theWorld.World;

public class WarpScript extends Script{
	
	private String dest;
	private int destX;
	private int destY;
	private Direction face;
	
	public WarpScript(String dest, int destX, int destY) { this(dest,destX,destY,Player.getInstance().getDir()); }

	public WarpScript(String dest, int destX, int destY, Direction face)
	{
		super();
		this.dest = dest;
		this.destX = destX;
		this.destY = destY;
		this.face = face;
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
			Player.getInstance().setLocation(destX,destY,face);
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
