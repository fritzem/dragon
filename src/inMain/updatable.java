package inMain;

import theWorld.State;

public interface updatable {
	
	default public void addUpdate()
	{
		State.addUpdate(this);
	}
	default public void removeUpdate()
	{
		State.removeUpdate(this);
	}
	
	//Return true to purge
	public boolean update(long delta);
}
