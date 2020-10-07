package inMain;

import java.util.HashMap;

import graphics.ISprite;
import graphics.PaletteSprite;
import graphics.SpriteLoader;

public class SpriteRepo {
	
	public static HashMap<String, PaletteSprite> paletteSprites;
	public static ISprite[] tileSprites;
	public static ISprite[] characterSprites;
	
	public static void InitSprites()
	{
		SpriteLoader spriteLoader = new SpriteLoader();
		
		paletteSprites = spriteLoader.loadSpriteSets();
		tileSprites = spriteLoader.loadTiles();
		characterSprites = spriteLoader.loadCharacters();
	}
	
	public static PaletteSprite getSprite(String name)
	{
		return paletteSprites.get(name);
	}
}
