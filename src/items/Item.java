package items;

import interfaces.IMenu;
import interfaces.MenuItem;

public abstract class Item implements MenuItem{
	private String name;
	protected IMenu m;
	
	public Item(String name) {
		this.name = name;
	}
	
	public boolean execute(IMenu m) {
		this.m = m;
		function();
		return false;
	}
	
	public abstract void function();

	public String getName() {
		return name;
	}
	
}
