package inMain;

import java.util.Random;

public enum Direction {
	UP(0, -1, 2), DOWN(0, 1, 0), LEFT(-1, 0, 1), RIGHT(1, 0, 3);
	
	public final int xOff;
	public final int yOff;
	public final int spriteOff;
	
	private static Random rand = new Random();
	
	Direction(int xOff, int yOff, int spriteOff)
	{
		this.xOff = xOff;
		this.yOff = yOff;
		this.spriteOff = spriteOff;
	}
	
	public static Direction parse(String str) {
		switch (str.toUpperCase())
		{
			case "UP":
				return Direction.UP;
			case "DOWN":
				return Direction.DOWN;
			case "LEFT":
				return Direction.LEFT;
			case "RIGHT":
				return Direction.RIGHT;
			default:
				return Direction.DOWN;
		}
	}
	
	public static Direction parse(int val) {
		switch (val)
		{
			case 2:
				return Direction.UP;
			case 0:
				return Direction.DOWN;
			case 1:
				return Direction.LEFT;
			case 3:
				return Direction.RIGHT;
			default:
				return Direction.DOWN;
		}
	}
	
	public static Direction inverse(Direction dir) {
		switch (dir)
		{
			case UP:
				return Direction.DOWN;
			case DOWN:
				return Direction.UP;
			case LEFT:
				return Direction.RIGHT;
			case RIGHT:
				return Direction.LEFT;
			default:
				return Direction.DOWN;
		}
	}
	
	public static Direction randDirection() {
		return parse(rand.nextInt(4));
	}
}
