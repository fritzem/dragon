package actions;

import inMain.Direction;
import inMain.Player;
import interfaces.MenuItem;
import theWorld.State;

public class Talk implements MenuItem{

	@Override
	public boolean execute() {
		int[] coords = Player.getInstance().getFaceBlock();
		State.getMap().talk(coords[0], coords[1]);
		return false;
	}

	public String getName() {
		return "TALK";
	}

}
