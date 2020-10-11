package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface ISprite {
	public void draw(Graphics2D g, int x, int y);
	public void draw(Graphics2D g, int x, int y, int cap);
	public void drawFrame(Graphics2D g, int x, int y, int frame);
	public void update();
}
