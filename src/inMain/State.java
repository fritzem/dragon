package inMain;
import java.util.ArrayList;
import java.util.Stack;

import graphics.Map;
import interfaces.Menu;
import interfaces.TextMenu;

public class State {
	
	//Generic Arrays
	private Stack<focusable> focus;
	private ArrayList<drawable> draw;
	private static ArrayList<updatable> updates;
	
	
	public static ArrayList<Menu> menus;
	
	private static Map map;
	private Player player;
	
	private static State stateInstance;
	
	boolean[] inpTest;
	private int fps;
	
	public State()
	{
		focus = new Stack<>();
		updates = new ArrayList<updatable>();
		
		stateInstance = this;
		inpTest = new boolean[10];
		fps = 0;
		menus = new ArrayList<Menu>();
	}
	
	public static boolean validSpot(int x, int y)
	{
		if (x > 0 && y > 0 && (y + 1 < map.map[0].length) && (x + 1 < map.map.length))
			if (!map.mapColl[x][y])
				return true;
		return false;
	}
	
	public static void setFPS(int fps)
	{
		stateInstance.fps = fps;
	}
	public static int getFPS()
	{
		return stateInstance.fps;
	}
	
	public static void setPlayer(Player p)
	{
		stateInstance.player = p;
	}
	
	public static Player getPlayer()
	{
		return stateInstance.player;
	}
	
	public static void setMap(Map m)
	{
		stateInstance.map = m;		
	}
	public static Map getMap()
	{
		return stateInstance.map;
	}
	
	public static State getInstance()
	{
		return stateInstance;
	}
	
	//Related to focus
	public static void addFocus(focusable f)
	{
		stateInstance.focus.push(f);
	}
	
	public static void popFocus()
	{
		stateInstance.focus.pop();
	}
	
	public static focusable peekFocus()
	{
		return stateInstance.focus.peek();
	}
	
	public static boolean noFocus()
	{
		return stateInstance.focus.empty();
	}
	
	public static void enqueue(Menu m)
	{
		menus.add(m);
	}
	
	public static void dequeue(Menu m)
	{
		menus.remove(m);
		menus.trimToSize();
	}
	
	public static void noqueue()
	{
		menus.clear();
		menus.trimToSize();
	}
	
	public static void addUpdate(updatable update)
	{
		updates.add(update);
	}
	
	public static void removeUpdate(updatable update)
	{
		updates.remove(update);
		updates.trimToSize();
	}
	
	public static void update()
	{
		for (updatable u : updates)
		{
			u.update(0l);
		}
	}
}
