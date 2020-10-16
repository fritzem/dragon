package theWorld;
import java.util.ArrayList;
import java.util.Stack;

import inMain.Player;
import inMain.focusable;
import inMain.updatable;
import interfaces.Menu;


public class State {
	
	//Whatever is currently receiving the input
	private static Stack<focusable> focus;
	
	//Whatever is currently receiving the only updates
	private static Stack<Update> updateStack = new Stack<Update>();
	
	public static ArrayList<Menu> menus;
	
	private static World world;
	
	
	private static Player player;
	
	//HP below certain threshold
	public static boolean danger = false;
	
	
	private static int fps;
	
	public State()
	{
		focus = new Stack<>();
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
		return world.getMap().validLocation(x, y);
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
		if (updateStack.empty())
			updateStack.push(new Update(update));
		else
			updateStack.peek().addUpdate(update);
	}
	
	public static void pushUpdate(updatable update)
	{
		System.out.println("push");
		updateStack.push(new Update(update));
	}
	
	public static void update(long delta)
	{
		if (!updateStack.empty())
		{
			if (updateStack.peek().update(delta))
				updateStack.pop();
		}
	}
	
}
