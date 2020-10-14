package theWorld;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	public int[][] roof;
	
	private boolean roofed;
	private boolean indoors;
	
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
			
			
			//Gathers layers, "World", "Roof"
			HashMap<String, Element> layers = getElementsNamed(e, "layer");
			
			//background info, if not found, set to black
			String background = findAttribute(layers.get("World"), "background");
			if (background != null)
				this.background = Integer.parseInt(background);
			else
				this.background = 11;
			
			//gathers tile information
			encodeTiles(layers.get("World").getElementsByTagName("data").item(0).getTextContent());
			if (layers.containsKey("Roof"))
			{
				roofed = true;
				roof = parseTiles(layers.get("Roof").getElementsByTagName("data").item(0).getTextContent());
			} else {
				roofed = false;
			}
			indoors = false;
			
			
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
				int locX = (int) ((Double.parseDouble(entity.getAttribute("x"))) + (tileSize / 2)) / tileSize;
				int locY = (int) ((Double.parseDouble(entity.getAttribute("y"))) - (tileSize / 2)) / tileSize;
				
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
	
	public HashMap<String, Element> getElementsNamed(Element e, String eleSeek)
	{
		NodeList elements = e.getElementsByTagName(eleSeek);
		HashMap<String, Element> map = new HashMap<String, Element>();
		for (int i = 0; i < elements.getLength(); i++)
		{
			map.put(((Element) elements.item(i)).getAttribute("name"), (Element) elements.item(i));
		}
		return map;
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
		if (!mapColl[x][y])
			return false;
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
			int[] validTiles = new int[]{4,5,7,8,9,12,13,14,15,16,17,19,20,21,22,23};
			mapColl[i % width][i / height] = false;
			for (int k = 0; k < validTiles.length; k++)
				if (Integer.parseInt(arrOfTiles[i]) == validTiles[k])
				{
					mapColl[i % width][i / height] = true;
					break;
				}
		}
	}
	
	private int[][] parseTiles(String s)
	{
		String[] arrOfTiles = s.split(",");
		int[][] tiles = new int[width][height];
		for (int i = 0; i < arrOfTiles.length; i++)
		{
			arrOfTiles[i] = arrOfTiles[i].trim();
			tiles[i % width][i / height] = Integer.parseInt(arrOfTiles[i]) - 1;
		}
		return tiles;
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
	
	public void drawTile(Graphics2D g, int x, int y, int tX, int tY)
	{
		if (visible(tX, tY))
			SpriteRepo.tileSprites[map[tX][tY]].draw(g, x, y);
		else {
			if (roof[tX][tY] != -1)
				SpriteRepo.tileSprites[roof[tX][tY]].draw(g, x, y);
		}
	}
	
	//Returns true or false depending on whether the tile is visible
	public boolean visible(int x, int y)
	{
		if (!roofed)
			return true;
		return ((!indoors && roof[x][y] == -1) ||
				 indoors && roof[x][y] != -1);
	}
	
	public void setIndoors(boolean b) {indoors = b;}
	
	public boolean indoors() {return indoors;}
	
	
	
}
