package events;

import theWorld.State;

public class OutdoorEvent implements Event {

	public void activate() {
		State.getMap().setIndoors(false);
	}
	
}
