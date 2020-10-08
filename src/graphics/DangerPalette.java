package graphics;

import inMain.updatable;
import theWorld.State;

public class DangerPalette extends PaletteSubject implements IPalette {
	
	private IPalette normalPalette;
	private IPalette dangerPalette;
	
	private boolean cachedDanger;
	
	public DangerPalette(IPalette normal, IPalette danger)
	{
		super();
		cachedDanger = false;
		
		normalPalette = normal;
		dangerPalette = danger;
	}
	
	public void addObserver(ISprite sprite)
	{
		sprites.add(sprite);
		normalPalette.addObserver(sprite);
		dangerPalette.addObserver(sprite);
	}
	
	public boolean update(long delta) {
		if (State.danger != cachedDanger)
		{
			update();
			cachedDanger = State.danger;
		}
		return false;
	}

	public int getColor(int i) {
		if (State.danger)
			return dangerPalette.getColor(i);
		else
			return normalPalette.getColor(i);
	}


	public Palette getBase() {
		return normalPalette.getBase();
	}

}
