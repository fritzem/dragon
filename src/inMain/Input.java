package inMain;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import graphics.Display;
import interfaces.CommandMenu;
import interfaces.FrameCounterMenu;
import interfaces.MiniStatusMenu;

public class Input {
	private static Input inputState;
	private boolean[] keys;
	
	public Input(Display d) 
	{
		inputState = this;
		keys = new boolean[60];
		
		d.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					keys[0] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					keys[1] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					keys[2] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					keys[3] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_Z) {
					keys[4] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_X) {
					keys[5] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_1) { 
					new MiniStatusMenu().execute();
				}
				if (e.getKeyCode() == KeyEvent.VK_2) {
					new FrameCounterMenu().execute();
				}
				if (e.getKeyCode() == KeyEvent.VK_C) {
					keys[6] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					State.noqueue();
				}
			}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					keys[0] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					keys[1] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					keys[2] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					keys[3] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_Z) {
					keys[4] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_X) {
					keys[5] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_C) {
					keys[6] = false;
				}
			}
			
			public void keyTyped(KeyEvent e) {

			}
			
		});
		
		
	}
	
	public boolean[] getKeys()
	{
		return keys;
	}
	
	public static Input getInput()
	{
		return inputState;
	}
}
