package graphics;

import java.util.ArrayList;

import inMain.updatable;

public abstract class PaletteSubject implements updatable{
	
	
	protected ArrayList<ISprite> sprites;
	
	public PaletteSubject()
	{
		sprites = new ArrayList<ISprite>();
		staticUpdate();
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
	
	public abstract boolean update(long delta);
}
