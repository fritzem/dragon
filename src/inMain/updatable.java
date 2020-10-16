package inMain;

import theWorld.State;

public interface updatable {
	
	default public void addUpdate()
	{
		State.addUpdate(this);
	}
	default public void pushUpdate()
	{
		State.pushUpdate(this);
	}
	
	//Return true to purge
	public boolean update(long delta);
}
