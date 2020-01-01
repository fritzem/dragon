package theWorld;

import java.io.File;
import java.io.IOException;
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

public class Map {
	
	public int[][] map;
	public String[][][] events;
	
	private int width;
	private int height;
	
	
	public boolean[][] mapColl;
	
	public Map()
	{
		map = new int[100][100];
		map[10][10] = 26;
	}
	
	public Map(String filename, String worldName)
	{
		File file = new File("data/" + worldName + "/" + filename + ".tmx");
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
			
			//gathers tile information
			NodeList tiles = mapDoc.getElementsByTagName("data");
			encodeTiles(tiles.item(0).getTextContent());
			
			//gathers event information
			int tileSize = Integer.parseInt(e.getAttribute("tilewidth"));
			NodeList events = mapDoc.getElementsByTagName("object");
			for (int i = 0; i < events.getLength(); i++)
			{
				Element event = (Element) events.item(i);
				System.out.println("Event at " 
						+ (int) Double.parseDouble(event.getAttribute("x")) / tileSize 
						+ " " 
						+ (int) Double.parseDouble(event.getAttribute("y")) / tileSize);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to create map " + filename);
		}
		
		
		
		
		
		
		
		
		
		/**
		Scanner sc;
		try {
			sc = new Scanner(new File("data/" + worldName + "/" + filename + ".tmx"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		int x = Integer.parseInt(sc.next());
		int y = Integer.parseInt(sc.next());
		map = new int[x][y];
		mapColl = new boolean[x][y];
		sc.nextLine();
		String line;
		int index = 0;
		while (sc.hasNext())
		{
			line = sc.nextLine();
			String[] arrOfTiles = line.split(",");
			for (int i = 0; i < arrOfTiles.length; i++)
			{
				map[i][index] = Integer.parseInt(arrOfTiles[i]) - 1;
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
					mapColl[i][index] = false;
					break;
				default:
					mapColl[i][index] = true;
				}
				
			}
			index++;
		} */
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
	
	public int[][] getMap()
	{
		return null;
	}
}
