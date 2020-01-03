package theWorld;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import inMain.Player;
import inMain.focusable;
import inMain.updatable;
import interfaces.Menu;


public class State {
	
	//Generic Arrays
	private static Stack<focusable> focus;
	private static ArrayList<updatable> updates;
	private static ArrayList<updatable> tempUpdates;
	public static ArrayList<Menu> menus;
	
	private static Iterator<updatable> upIt;
	
	private static World world;
	
	
	private static Player player;
	
	
	private static int fps;
	
	public State()
	{
		focus = new Stack<>();
		updates = new ArrayList<updatable>();
		tempUpdates = new ArrayList<updatable>();
		menus = new ArrayList<Menu>();
		fps = 0;
		
		loadWorld("Testland");
	}
	
	public static void setFPS(int newFPS)
	{
		fps = newFPS;
	}
	public static int getFPS()
	{
		return fps;
	}
	
	public static void setPlayer(Player p)
	{
		player = p;
	}
	
	public static Player getPlayer()
	{
		return player;
	}
	
	
	/*
	 * Deal with world related information
	 */
	public static void loadWorld(String name)
	{
		world = new World(name);		
	}
	public static Map getMap()
	{
		return world.getMap();
	}
	
	public static boolean validSpot(int x, int y)
	{
		return true;
		/*
		if (x > 0 && y > 0 && (y + 1 < map.map[0].length) && (x + 1 < map.map.length))
			return (!map.mapColl[x][y]);
		return false; */
	}
	
	
	/*
	 * Objects that take input are focusable
	 * The object at the top of the stack is what receives all input
	 */
	public static void addFocus(focusable f)
	{
		focus.push(f);
	}
	
	public static void popFocus()
	{
		focus.pop();
	}
	
	public static focusable peekFocus()
	{
		return focus.peek();
	}
	
	public static boolean noFocus()
	{
		return focus.empty();
	}
	
	
	/*
	 * List of open menus the Display uses to draw
	 * list is ordered back to front
	 */
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
	
	
	/*
	 * All objects that require game updates (or ticks) are 'updatable objects'
	 * You can add to and remove from this list, game loop calls update()
	 */
	public static void addUpdate(updatable update)
	{
		tempUpdates.add(update);
	}
	
	public static void removeUpdate(updatable update)
	{
		updates.remove(update);
		updates.trimToSize();
	}
	
	public static void update()
	{
		updateArraylist();
		upIt = updates.iterator();
		while (upIt.hasNext())
		{
			updatable u = upIt.next();
			if (u.update(0l))
				upIt.remove();
		}
	}
	
	public static void updateArraylist()
	{
		tempUpdates.trimToSize();
		for (updatable u : tempUpdates)
		{
			updates.add(u);
		}
		tempUpdates = new ArrayList<updatable>();
	}
}
