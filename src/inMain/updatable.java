package inMain;

import theWorld.State;

public interface updatable {
	
	//Scoped-like update system
	default public void addUpdate()
	{
		State.addUpdate(this);
	}
	default public void pushUpdate()
	{
		State.pushUpdate(this);
	}
	
	//Static updates
	default public void staticUpdate()
	{
		State.staticUpdate(this);
	}
	
	//Return true to purge
	public boolean update(long delta);
}
