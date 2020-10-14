package events;

import theWorld.State;

public class IndoorEvent implements Event{

	public void activate() {
		State.getMap().setIndoors(true);
	}

}
