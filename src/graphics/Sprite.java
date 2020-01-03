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
	
	
	public Sprite(String filename, int tileSize)
	{
		this.tileSize = tileSize;
		try {
			sprite = ImageIO.read(new File("resources/" + filename + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Sprite(String filename)
	{
		try {
			sprite = ImageIO.read(new File("resources/" + filename + ".png"));
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
		
		BufferedImage image = null;
		RescaleOp contrast = new RescaleOp(0.9f, 0, (RenderingHints) null);
		RescaleOp brightness = new RescaleOp(0.6f, 0, null);
		
		float[] factors = new float[] {1.45f, 1.45f, 1.45f}; 
		float[] offsets = new float[] {0.0f, 150.0f, 0.0f}; 
	    RescaleOp rop = new RescaleOp(factors, offsets, null); 
		
		//BufferedImage img = rop.filter(sprite, null);
		
		//g.drawImage(sprite, x, y, sprite.getWidth(), sprite.getHeight(), null);
		g.drawImage(sprite, x, y, null);
		//new float[]{1.1f, 1.5f, 0.5f, 1f}, // scale factors for red, green, blue, alpha
        //new float[]{0, -150, 0, 0}, // offsets for red, green, blue, alpha sunset
		
	}
	
	public void draw(Graphics2D g, int x, int y, int cap)
	{
		if (cap <= y)
			return;
		g.drawImage(sprite, x, y, null);
	}
	
	public int getWidth()
	{
		return sprite.getWidth();
	}
	
	public int getHeight()
	{
		return sprite.getHeight();
	}
	
	public int getSize()
	{
		return tileSize;
	}
}
