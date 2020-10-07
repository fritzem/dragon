package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PaletteSprite implements ISprite{
	
	private int[][] spriteData;
	private BufferedImage sprite;
	private IPalette palette;
	
	public PaletteSprite()
	{
		spriteData = new int[16][16];
		palette = Palette.paletteFactory("idk");
	}
	
	public PaletteSprite(BufferedImage img, IPalette palette)
	{
		palette.addObserver(this);
		sprite = img;
		spriteData = new int[img.getWidth()][img.getHeight()];
		this.palette = palette;
		for (int i = 0; i < img.getWidth(); i++)
		{
			for (int k = 0; k < img.getHeight(); k++)
			{
				//palette.parseRGB(img.getRGB(i, k));
				spriteData[i][k] = palette.parseRGB(img.getRGB(i, k));
			}
		}
	}
	
	public void update()
	{
		updateImage();
	}
	
	public void updateImage()
	{
		for (int i = 0; i < sprite.getWidth(); i++)
		{
			for (int k = 0; k < sprite.getHeight(); k++)
			{
				if (spriteData[i][k] == -1) continue;
				sprite.setRGB(i, k, palette.getColor(spriteData[i][k]));
			}
		}
	}
	
	public void draw(Graphics2D g, int x, int y)
	{
		g.drawImage(sprite, x, y, null);
	}
	
	public void draw(Graphics2D g, int x, int y, int cap)
	{
		if (cap > y)
			draw(g, x, y);
	}
	
	public void drawFrame(Graphics2D g, int x, int y, int frame)
	{
		g.drawImage(sprite, x, y, x + 16, y + 16, frame * 16, 0, frame * 16 + 16, 16, null);
	}
	
	public void apply(BufferedImage img, int x, int y)
	{
		for (int i = 0; i < 16; i++)
		{
			for (int k = 0; k < 16; k++)
			{
				if (spriteData[i][k] == -1)
					continue;
				img.setRGB(x + i, y + k, palette.getColor(spriteData[i][k]));
			}
		}
	}
	
}
