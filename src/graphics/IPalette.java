package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface IPalette {
	public int getColor(int i);
	public default boolean parsable(BufferedImage img)
	{
		for (int i = 0; i < img.getWidth(); i++)
		{
			for (int k = 0; k < img.getHeight(); k++)
			{
				if (parseRGB(img.getRGB(i, k)) == -1)
					return false;
			}
		}
		return true;
	}
	public default int parseRGB(int val)
	{
		int[] colors = getBase().getColors();
		
		for (int i = 0; i < colors.length; i++)
		{
			if (val == colors[i])
				return i;
		}
		return -1;
	}
	
	//For palettes that don't cause changes on their own.
	public default void addObserver(ISprite sprite) {return;}
	public Palette getBase();
}
