package items;

import interfaces.MenuItem;

public abstract class Item implements MenuItem{
	private String name;
	
	public Item(String name) {
		this.name = name;
	}
	public boolean execute() {
		return false;
	}

	public String getName() {
		return name;
	}
	
}
