package interfaces;

public interface MenuItem {	
	/*
	 * What to do when this menuItem is chosen
	 * Return true to clear all menus upon execution
	 */
	public boolean execute(IMenu m);
	public String getName();
}
