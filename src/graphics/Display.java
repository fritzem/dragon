package graphics;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import inMain.Player;
import interfaces.Menu;
import interfaces.TextMenu;
import theWorld.Entity;
import theWorld.Map;
import theWorld.State;

public class Display extends Canvas{
	
	public static int fade = 0;
	
	HashMap<String, PaletteSprite> paletteSprites;
	ISprite[] tileSprites;
	ISprite[] characterSprites;
	
	private JFrame display;
	
	private int scale;
	
	private BufferStrategy buffer;
	private Graphics2D g;

	
	public Display()
	{
		display = new JFrame();
		scale = 4;
		this.setSize(256 * scale ,224 * scale);
		display.add(this);
		display.setTitle("Dragon Warrior");
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
		//System.out.println("got to draw");
		//trash testers
		
		g.scale(scale, scale);
		//Draw the world
		drawMap();
		
		//Draw the player
		drawPlayer();
		
		drawEntities();
		drawInterface();
		
		
		
	}
	
	public void drawInterface()
	{
		for (Menu m : State.menus)
			m.draw(g, characterSprites);
	}
	
	public void drawPlayer()
	{
		Player.getInstance().draw(g);
	}
	
	public void drawEntities()
	{
		ArrayList<Entity> entities = State.getMap().getEntities();
		int[] loc = State.getPlayer().getLocation();
		for (Entity i : entities)
		{
			i.draw(g);
			//i.draw(g, (i.getX() - loc[0] + 7) * 16 + 8 + Player.getInstance().getSlideX(), (i.getY() - loc[1] + 6) * 16 + 8 + Player.getInstance().getSlideY());
		}
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
		int tileSize = 16; //broken??
		
		int slideX = Player.getInstance().getSlideX();
		int slideY = Player.getInstance().getSlideY();
		
		for (int i = -2; i < xTILES; i++) 
		{
			for (int k = -2; k < yTILES; k++)
			{
				//off the grid?
				if (tX + i < 0 || tX + i >= m.map.length || tY + k < 0 || tY + k >= (m.map[0].length))
				{
					if (!m.indoors())
						tileSprites[m.getBackground()].draw(g,  (i% xTILES) * tileSize + slideX + 8,  k * tileSize + slideY + 8);
				}
				else
					m.drawTile(g, (i % xTILES) * tileSize + slideX + 8, k * tileSize + slideY + 8, tX + i, tY + k);
				//tileSprites[m.map[tX + i][tY + k]].draw(g, (i % xTILES) * tileSize + slideX + 8, k * tileSize + slideY + 8);
			}
		}
	}
	
	public void initSprites()
	{	
		SpriteLoader spriteLoader = new SpriteLoader();
		
		paletteSprites = spriteLoader.loadSpriteSets();
		tileSprites = spriteLoader.loadTiles();
		characterSprites = spriteLoader.loadCharacters();
		
		
		//characters = readSheet("dragonFont8", 8, 0);
		//tiles = readSheet("overworldSprites", 16, 1);
		//entities = readSheet("entitySprites", 16, 0);
		
		
		//g.drawImage(sprite, x, y, sprite.getWidth(), sprite.getHeight(), null);

		//char sprites
		/*
		characters = new Sprite[45];
		Sprite characterSheet = new Sprite("text");
		for (int i = 0; i < 45; i++)
		{
			characters[i] = new Sprite(characterSheet, (i % 16) * 16, i / 16 * 16, 7, 7);
		} */
		
		
		
		
		
		
		//tile sprites
		
		//entity sprites
	}
	
	public int anim()
	{
		int test = 0;
		if (System.currentTimeMillis() % 550 > 275)
			test = 1;
		return test;
	}
	
	public JFrame getFrame()
	{
		return display;
	}

}
