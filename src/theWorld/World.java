package theWorld;

import java.util.ArrayList;

public class World {
	
	private static ArrayList<Map> maps;
	private static int index;
	
	public World(String name)
	{
		maps = new ArrayList<Map>();
		maps.add(new Map("dragon-E", name));
		maps.add(new Map("test1", name));
		maps.add(new Map());
		maps.add(new Map("town", name));
		
		index = 0;
	}
	
	public Map getMap()
	{
		return maps.get(index);
	}
	
	public static void setMap(int i)
	{
		index = i;
	}
	
	public static void setMap(String s)
	{
		for (int i = 0; i < maps.size(); i++)
		{
			if (maps.get(i).getName().compareTo(s) == 0)
			{
				index = i;
				break;
			}
		}
	}
	
	public static void queryEvent(int x, int y)
	{
		maps.get(index).queryEvent(x, y);
	}
}
