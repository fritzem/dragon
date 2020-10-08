package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FadePalette extends PaletteSubject implements IPalette{
	
	private IPalette[] palettes;
	private int cachedFade;
	
	public FadePalette(File file)
	{
		super();
		cachedFade = 0;
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		palettes = new IPalette[img.getHeight()];
		for (int i = 0; i < img.getHeight(); i++)
		{
			palettes[i] = new Palette(img.getSubimage(0, i, img.getWidth(), 1));
		}
	}
	
	public int getColor(int i) {
		return palettes[Display.fade].getColor(i);
	}

	public Palette getBase() {
		return palettes[0].getBase();
	}
	
	public boolean update(long delta) {
		if (Display.fade != cachedFade)
		{
			update();
			cachedFade = Display.fade;
		}
		return false;
	}

}
