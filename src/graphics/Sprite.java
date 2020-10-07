package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

public class Sprite {
	
	private BufferedImage spriteSheet;
	private BufferedImage sprite;
	private int tileSize;
	
	public Sprite(File file)
	{
		try {
			sprite = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Sprite(Sprite s, int x1, int y1, int x2, int y2)
	{
		sprite = s.sprite.getSubimage(x1, y1, x2, y2);
	}
	
	
	public void draw(Graphics2D g, int x, int y)
	{
		g.drawImage(sprite, x, y, null);	
	}
	
	public void draw(Graphics2D g, int x, int y, int cap)
	{
		if (cap <= y)
			return;
		draw(g, x, y);
	}
	
	public int getWidth()
	{
		return sprite.getWidth();
	}
	
	public int getHeight()
	{
		return sprite.getHeight();
	}
	
}
