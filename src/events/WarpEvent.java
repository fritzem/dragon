package events;

import org.w3c.dom.Element;

import inMain.Direction;
import scripts.WarpScript;

public class WarpEvent implements Event{
	private String dest;
	private int destX;
	private int destY;
	private Direction face;
	public WarpEvent(Element event) throws NumberFormatException, Exception
	{
		dest = findAttribute(event, "dest");
		destX = Integer.parseInt(findAttribute(event, "destX"));
		destY = Integer.parseInt(findAttribute(event, "destY"));
		try {
			face = Direction.parse(findAttribute(event, "face"));
		} catch (Exception e) {
			face = null;
		}
	}
	public WarpEvent(String dest, int destX, int destY, Direction face)
	{
		this.dest = dest;
		this.destX = destX;
		this.destY = destY;
		this.face = face;
	}
	
	public void activate() {
		if (face == null)
			new WarpScript(dest, destX, destY);
		else
			new WarpScript(dest, destX, destY, face);
	}

}
