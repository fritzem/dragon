package theWorld;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import events.Event;
import events.EventBuilder;
import inMain.Direction;
import inMain.Player;
import inMain.SpriteRepo;
import interfaces.TextMenu;
import interfaces.IMenu;
import interfaces.MenuFactory;
import interfaces.NullMenu;

public class Map {
	
	public int[][] map;
	public boolean[][] mapColl;
	public Event[][] events;
	
	private ArrayList<Entity> entities;
	
	private int width;
	private int height;
	
	private String name;
	
	private int background;
	
	public Map()
	{
		width = 100;
		height = 100;
		map = new int[100][100];
		events = new Event[100][100];
		map[10][10] = 26;
		
		name = "nullland";
		background = 23;
	}
	
	public Map(String filename, String worldName)
	{
		
		File file = new File("data/" + worldName + "/" + filename + ".tmx");
		name = filename;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document mapDoc = builder.parse(file);
			
			//gathers map tag metadata
			Element e = mapDoc.getDocumentElement();
			width = Integer.parseInt(e.getAttribute("width"));
			height = Integer.parseInt(e.getAttribute("height"));
			
			map = new int[width][height];
			mapColl = new boolean[width][height];
			events = new Event[width][height];
			entities = new ArrayList<Entity>();
			
			//background info, if not found, set to black
			String background = findAttribute(e, "background");
			if (background != null)
				this.background = Integer.parseInt(background);
			else
				this.background = 23;
			
			//gathers tile information
			NodeList tiles = mapDoc.getElementsByTagName("data");
			encodeTiles(tiles.item(0).getTextContent());
			
			
			//begin gathering layered metadata
			int tileSize = Integer.parseInt(e.getAttribute("tilewidth"));
			NodeList groups = mapDoc.getElementsByTagName("objectgroup");
				
			//gathers event information
			NodeList events = ((Element) groups.item(0)).getElementsByTagName("object"); //replace these, they suck
			for (int i = 0; i < events.getLength(); i++)
			{
				Element event = (Element) events.item(i);
				int locX = (int) Double.parseDouble(event.getAttribute("x")) / tileSize;
				int locY = (int) Double.parseDouble(event.getAttribute("y")) / tileSize;
				this.events[locX][locY] = EventBuilder.buildEvent(event);
				System.out.println("Event at " + locX + " " + locY);
			}

			//gathers entity information
			NodeList entities = ((Element) groups.item(1)).getElementsByTagName("object");
			for (int i = 0; i < entities.getLength(); i++)
			{
				Element entity = (Element) entities.item(i);
				int locX = (int) Double.parseDouble(entity.getAttribute("x")) / tileSize;
				int locY = (int) Double.parseDouble(entity.getAttribute("y")) / tileSize;
				
				String sprite = getProperty(entity, "sprite");
				Direction facing = (propExists(entity, "facing") ? Direction.parse(getProperty(entity, "facing")) : Direction.DOWN);
				IMenu menu = (
						(propExists(entity, "menu")) ? MenuFactory.getMenu(getProperty(entity, "menu")) : //Pull a complicated menu from the simple factory
						(propExists(entity, "dialogue")) ? new TextMenu(getProperty(entity, "dialogue")) : //Or make a dialogue box on the fly
						new NullMenu() //Or nothing happens
						);
				Behavior behaves = (propExists(entity, "behavior") ? Behavior.parse(getProperty(entity, "behavior")) : Behavior.STILL);
				
				System.out.println("Creating sprite: " + sprite);
				this.entities.add(new Entity(locX, locY, facing, SpriteRepo.getSprite(sprite), menu, behaves));
				System.out.println("Entity at " + locX + " " + locY);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to create map " + filename);
		}
		

	}
	
	public String getProperty(Element e, String propSeek)
	{
		NodeList properties = ((Element) e.getElementsByTagName("properties").item(0)).getElementsByTagName("property");
		for (int i = 0; i < properties.getLength(); i++)
		{
			Element prop = (Element) properties.item(i);
			if (prop.getAttribute("name").compareTo(propSeek) == 0)
				return prop.getAttribute("value");
		}
		return "Property " + propSeek + " not found";
	}
	
	public boolean propExists(Element e, String propSeek)
	{
		NodeList properties = ((Element) e.getElementsByTagName("properties").item(0)).getElementsByTagName("property");
		for (int i = 0; i < properties.getLength(); i++)
		{
			Element prop = (Element) properties.item(i);
			if (prop.getAttribute("name").compareTo(propSeek) == 0)
				return true;
		}
		return false;
	}
	
	public void queryEvent(int x, int y)
	{
		if (withinValidRange(x,y) && events[x][y] != null)
			events[x][y].activate();
	}
	
	
	//returns true if tile is open for movement
	public boolean validLocation(int x, int y)
	{
		//limits you to the map
		if (!withinValidRange(x,y))
			return false;
		//limits you to col tiles
		/*
		if (x > 0 && y > 0 && (y + 1 < map.map[0].length) && (x + 1 < map.map.length))
			return (!map.mapColl[x][y]); */
		for (Entity i : entities)
		{
			if (i.getX() == x && i.getY() == y)
				return false;
		}
		int[] loc = Player.getInstance().getLocation();
		if (x == loc[0] && y == loc[1])
			return false;
		return true;
	}
	
	public boolean withinValidRange(int x, int y)
	{
		return (x >= 0 && y >= 0 && x < width && y < height);
	}
	
	private void encodeTiles(String s)
	{
		String[] arrOfTiles = s.split(",");
		for (int i = 0; i < arrOfTiles.length; i++)
		{
			arrOfTiles[i] = arrOfTiles[i].trim();
			map[i % width][i / height] = Integer.parseInt(arrOfTiles[i]) - 1;
			switch (Integer.parseInt(arrOfTiles[i]))
			{
			case 4:
			case 5:
			case 7:
			case 8:
			case 16:
			case 17: 
			case 19:
			case 20:
			case 25:
			case 26: 
			case 27:
			case 28:
			case 29:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 37:
			case 38:
			case 39:
			case 40:
			case 41:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
				mapColl[i % width][i / height] = false;
				break;
			default:
				mapColl[i % width][i / height] = true;
			}
			
		}
	}
	
	public String findAttribute(Element event, String find)
	{
		NodeList properties = event.getElementsByTagName("property");

		for (int i = 0; i < properties.getLength(); i++) 
		{
			if (((Element)properties.item(i)).getAttribute("name").compareTo(find) == 0)
			{
				return (((Element)properties.item(i)).getAttribute("value"));
			}
		}
		return null;
	}
	
	public int[][] getMap()
	{
		return null;
	}
	
	public int getBackground()
	{
		return background;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Entity> getEntities()
	{
		return entities;
	}
	
	public void talk(int x, int y)
	{
		for (Entity e : entities)
		{
			if (e.getX() == x && e.getY() == y)
			{
				e.talk();
				return;
			}
		}
		new TextMenu("There is no one there.").execute();
	}
}
