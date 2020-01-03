package events;

import org.w3c.dom.Element;

import inMain.Player;
import scripts.WarpScript;
import theWorld.World;

public class WarpEvent implements Event{
	private String dest;
	private int destX;
	private int destY;
	public WarpEvent(Element event) throws NumberFormatException, Exception
	{
		dest = findAttribute(event, "dest");
		destX = Integer.parseInt(findAttribute(event, "destX"));
		destY = Integer.parseInt(findAttribute(event, "destY"));
	}
	public void activate() {
		new WarpScript(dest, destX, destY);
	}

}
