package graphics;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import inMain.Player;
import interfaces.Menu;
import interfaces.TextMenu;
import theWorld.Map;
import theWorld.State;

public class Display extends Canvas{
	
	private JFrame display;
	
	private Sprite[] characters;
	private Sprite[] tiles;
	private Sprite[] player;
	private int size;
	private int scale;
	
	private BufferStrategy buffer;
	private Graphics2D g;

	
	public Display()
	{
		display = new JFrame();
		scale = 2;
		this.setSize(256 * scale ,224 * scale);
		display.add(this);
		display.setTitle("Shell");
		display.pack();
		display.setVisible(true);
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIgnoreRepaint(true);
		createBufferStrategy(2);
		buffer = getBufferStrategy();
		
		initSprites();
		
	}
	public void draw()
	{
		//Prepare graphics and prepare blank canvas
		g = (Graphics2D) buffer.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//Draw the actual game stuff
		drawGame();
		g.drawRect(0, 0, 257, 225);
		//Cleanup graphics stuff
		g.dispose();
		buffer.show();
	}
	
	public void drawGame()
	{
		//trash testers
		
		scale = 2;
		g.scale(scale, scale);
		
		//Draw the world
		drawMap();
		
		//Draw the player
		drawPlayer();
		
		drawInterface();
		
	}
	
	public void drawInterface()
	{
		for (Menu m : State.menus)
		{
			m.draw(g, characters);
		}
	}
	
	public void drawPlayer()
	{
		int test = 0;
		if (System.currentTimeMillis() % 550 > 275)
			test = 1;
		player[Player.getInstance().getDir() * 2 + test].draw(g, 7 * 16 + 8, 6 * 16 + 8);
	}
	
	public void drawMap()
	{
		g.setColor(Color.BLACK); //draw background layer
		g.fillRect(0, 0, getWidth(), getHeight());
		int xTILES = 17;
		int yTILES = 15;
		Map m = State.getMap();
		int[] loc = State.getPlayer().getLocation();
		int tX = loc[0] - 7;
		int tY = loc[1] - 6;
		
		int slideX = Player.getInstance().getSlideX();
		int slideY = Player.getInstance().getSlideY();
		if (slideX == 0 && slideY == 0)
		{
			Player.getInstance().doneMoving();
		}
		
		for (int i = -2; i < xTILES; i++) 
		{
			for (int k = -2; k < yTILES; k++)
			{
				//off the grid?
				if (tX + i < 0 || tX + i >= m.map.length || tY + k < 0 || tY + k >= (m.map[0].length))
					tiles[48].draw(g,  (i% xTILES) * size + slideX + 8,  k * size + slideY + 8);
				else
					tiles[m.map[tX + i][tY + k]].draw(g, (i % xTILES) * size + slideX + 8, k * size + slideY + 8);
			}
		}
	}
	
	public void initSprites()
	{	
		//g.drawImage(sprite, x, y, sprite.getWidth(), sprite.getHeight(), null);

		//char sprites
		/*
		characters = new Sprite[45];
		Sprite characterSheet = new Sprite("text");
		for (int i = 0; i < 45; i++)
		{
			characters[i] = new Sprite(characterSheet, (i % 16) * 16, i / 16 * 16, 7, 7);
		} */
		characters = readSheet("dragonFont8", 8, 0);
		
		//tile sprites
		tiles = readSheet("overworldSprites", 16, 1);
		
		//entity sprites
		player = new Sprite[8];
		Sprite playerSheet = new Sprite("entitySprites");
		for (int i = 0; i < 8; i++)
		{
			player[i] = new Sprite(playerSheet, (i * 16), 54, 16, 16);
		}
	}
	
	public Sprite[] readSheet(String name, int sprSize, int spacing)
	{
		Sprite tileSheet = new Sprite(name, sprSize);
		size = tileSheet.getSize() + spacing;
		int xSize = tileSheet.getWidth() / size;
		int ySize = tileSheet.getHeight() / size;
		Sprite[] arr = new Sprite[xSize * ySize];
		
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = new Sprite(tileSheet, (i % xSize) * size, (i / xSize) * size, sprSize, sprSize);
		}
		size -= spacing;
		return arr;
	}
	
	public JFrame getFrame()
	{
		return display;
	}

}
