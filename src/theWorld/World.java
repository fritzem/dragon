package theWorld;

import java.util.ArrayList;

public class World {
	
	private ArrayList<Map> maps;
	
	
	public World(String name)
	{
		maps = new ArrayList<Map>();
		maps.add(new Map("dragon-E", name));
		maps.add(new Map("test1", name));
		maps.add(new Map());
	}
	
	public Map getMap()
	{
		return maps.get(0);
	}
}
