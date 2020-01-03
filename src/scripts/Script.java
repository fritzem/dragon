package scripts;

import inMain.focusable;
import inMain.updatable;

public abstract class Script implements focusable, updatable{
	
	protected int ticks;
	
	public Script()
	{
		ticks = 0;
		
		addFocus();
		addUpdate();
	}
	public void input(){}
	
}
