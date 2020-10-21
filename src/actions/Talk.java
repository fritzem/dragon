package actions;

import inMain.Direction;
import inMain.Player;
import interfaces.IMenu;
import interfaces.Menu;
import interfaces.MenuItem;
import theWorld.State;

public class Talk implements MenuItem{

	@Override
	public boolean execute(IMenu m) {
		int[] coords = Player.getInstance().getFaceBlock();
		State.getMap().talk(coords[0], coords[1], Player.getInstance().getDir()).execute(m);
		return false;
	}

	public String getName() {
		return "TALK";
	}

}
