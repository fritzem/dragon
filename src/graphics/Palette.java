package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Palette implements IPalette{
	
	private int [] colors;
	
	/*
	public Palette(File file) throws IOException
	{
		this(ImageIO.read(file));		
	} */
	
	public Palette(BufferedImage file)
	{
		colors = new int[file.getWidth()];
		for (int i = 0; i < file.getWidth(); i++)
		{
			colors[i] = file.getRGB(i, 0);
		}
	}
	
	public Palette(int val0, int val1, int val2, int val3)
	{
		colors = new int[] {};
	}
	
	public static Palette paletteFactory(String id)
	{
		return new Palette(0x000000,0xFCC4D8,0xFCFCFC,0x2038EC);
	}
	
	public int getColor(int i)
	{
		return colors[i];
	}
	
	public int[] getColors()
	{
		return colors;
	}

	public Palette getBase() {
		return this;
	}

}
