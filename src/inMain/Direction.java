package inMain;

public enum Direction {
	UP(0, -1, 2), DOWN(0, 1, 0), LEFT(-1, 0, 1), RIGHT(1, 0, 3);
	
	public final int xOff;
	public final int yOff;
	public final int spriteOff;
	
	Direction(int xOff, int yOff, int spriteOff)
	{
		this.xOff = xOff;
		this.yOff = yOff;
		this.spriteOff = spriteOff;
	}
}
