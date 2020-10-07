package graphics;

import java.util.ArrayList;

import inMain.updatable;

public class PaletteSubject implements updatable{
	
	private int cachedFade;
	
	private ArrayList<ISprite> sprites;
	
	public PaletteSubject()
	{
		sprites = new ArrayList<ISprite>();
		cachedFade = 0;
		addUpdate();
	}
	public void addObserver(ISprite sprite)
	{
		sprites.add(sprite);
	}
	public void update()
	{
		for (ISprite sprite : sprites)
			sprite.update();
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
