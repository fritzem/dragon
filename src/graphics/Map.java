package graphics;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Map {
	
	public int[][] map;
	public boolean[][] mapColl;
	
	public Map()
	{
		map = new int[100][100];
		map[10][10] = 26;
	}
	
	public Map(String filename)
	{
		Scanner sc;
		try {
			sc = new Scanner(new File("resources/" + filename + ".tmx"));
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
		}
	}
	
	public int[][] getMap()
	{
		return null;
	}
}
