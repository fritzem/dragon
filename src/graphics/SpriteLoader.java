package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import services.JsonParser;

public class SpriteLoader {
	
	private File graphicsDir;
	private HashMap<String, PaletteSprite> spriteDatabase;
	
	public SpriteLoader()
	{
		this("default");
	}
	public SpriteLoader(String pack)
	{
		spriteDatabase = new HashMap<String, PaletteSprite>();
		graphicsDir = new File("resources/graphics/" + pack);
		
		
	      
		/*
		JsonParser parser = new JsonParser();
		parser.parse(new File("resources/graphics/greenyTiles/config.json"));
		System.out.println((String)parser.getObj("sizeX")); */
	}
	
	public HashMap<String, PaletteSprite> loadSpriteSets()
	{
		FilenameFilter textFileFilter = new FilenameFilter(){
	         public boolean accept(File dir, String name) {
	            return dir.isDirectory();
	         }
	      };
		File[] spriteSets = (new File(graphicsDir, "sets")).listFiles(textFileFilter);
		for(File set : spriteSets) {
			loadSpriteSet(set);
		}
		return spriteDatabase;
	}
	
	public ISprite[] loadTiles()
	{
		return readSheet(new File(graphicsDir, "/tiles"), "overworldSprites.png", 16, 1);
	}
	
	public ISprite[] loadCharacters()
	{
		return readSheet(new File(graphicsDir, "/tiles"), "dragonFont8.png", 8, 0);
	}
	
	public void loadSpriteSet(File dir)
	{
		File palettesDir = new File(dir + "/palettes");
		IPalette pal = claimPalette(palettesDir);
		
		//If there's a config file use it to read the sprites
		File config = new File(dir + "/config.json");
		
		FilenameFilter spriteFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".png");
			}
		};
		File[] sprites = dir.listFiles(spriteFilter);
		
		if (config.isFile())
		{
			JsonParser parser = new JsonParser();
			parser.parse(config);
			for (File sprite : sprites) {
				ArrayList<Object> names = (ArrayList<Object>) parser.getObj(rmEx(sprite.getName()));
				loadPaletteSprites(sprite, pal, names);
			}
		}
		else
		{
			//else just pull all the pictures as sprites
			for (File sprite : sprites) {
				loadPaletteSprite(sprite, pal);
			}
		}
		
		
	}
	
	public void loadPaletteSprite(File sprite, IPalette palette)
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(sprite);
		} catch (IOException e) {
			e.printStackTrace();
		}
		spriteDatabase.put(rmEx(sprite.getName()), new PaletteSprite(img, palette));
	}
	
	public void loadPaletteSprites(File sprites, IPalette palette, ArrayList<Object> names)
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(sprites);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < img.getWidth() / 16; i++)
		{
			spriteDatabase.put((String)names.get(i), new PaletteSprite(img.getSubimage(i * 16, 0, 16, 16), palette));
		}
	}
	
	public ISprite[] readSheet(File dir, String filename, int sprSize, int spacing)
	{
		BufferedImage tileSheet = null;
		try {
			tileSheet = ImageIO.read(new File(dir, "/" + filename));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(filename);
		}
		
		ArrayList<IPalette> paletteSet = genPaletteSet(new File(dir, "/palettes"));
		
		int size = sprSize + spacing;
		int xSize = tileSheet.getWidth() / size;
		int ySize = tileSheet.getHeight() / size;
		ISprite[] arr = new ISprite[xSize * ySize];
		
		for (int i = 0; i < arr.length; i++)
		{
			BufferedImage img = tileSheet.getSubimage((i % xSize) * size, (i / xSize) * size, sprSize, sprSize);
			IPalette pal = findPalette(paletteSet, img);
			if (pal != null)
				arr[i] = new PaletteSprite(img, pal);
			else
				System.out.println("cannot find valid palette, replace this with a null sprite " + i + " " + filename);
		}
		return arr;
	}
	
	public IPalette claimPalette(File dir)
	{
		dir = new File(dir + "/palette.png");
		if (dir.exists()) 
			return new FadePalette(dir);
		return null;
	}
	
	//Returns the palette that successfully parses
	public IPalette findPalette(ArrayList<IPalette> paletteSet, BufferedImage img)
	{
		for (IPalette pal : paletteSet)
		{
			if (pal.parsable(img))
				return pal;
		}
		return null;
	}
	
	//Returns an array of palettes from a directory
	public ArrayList<IPalette> genPaletteSet(File dir)
	{
		ArrayList<IPalette> pals = new ArrayList<IPalette>();
		File[] paletteDirs = dir.listFiles();
		for (File file : paletteDirs)
		{
			IPalette out = paletteBuilder(file);
			if (out != null)
				pals.add(out);
		}
		return pals;
	}
	
	/*
	 * Name of IPalette = name of directory
	 * Default = palette.png
	 * Danger = danger.png
	 * hit = hit.png
	 */
	public IPalette paletteBuilder(File dir)
	{
		File def = new File(dir, "/palette.png");
		if (!def.exists())
			return null;
		IPalette pal = new FadePalette(def);
		File dan = new File(dir, "/danger.png");
		if (dan.exists()) {
			
		}
		return pal;
	}

	public String rmEx(String name)
	{
		return name.substring(0, name.lastIndexOf("."));
	}
}
