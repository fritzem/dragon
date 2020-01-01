package inMain;
import javax.swing.*;

import graphics.Display;
import interfaces.MiniStatusMenu;
import interfaces.TextMenu;
import theWorld.Map;
import theWorld.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
public class Game {

	private Display display;
	private Input input;
	private State state;
	
	public static int frames;
	
	private Boolean active;
	public static void main(String[] args) {
		Game game = new Game();
	}
	
	public Game()
	{
		state = new State();
		display = new Display();
		input = new Input(display);
		
		frames = 0;
		
		Player p = Player.getInstance();
		
		active = true;
		loop();
	}
	public void loop()
	{
		int targetFPS = 100;

		
		double nsPerFrame = 1000000000.0 / targetFPS;
		
		boolean active = true;
		long lastLoopTime = System.nanoTime();
		long secondTime = System.currentTimeMillis();
		long delta = 0;
		
		//targetting 100 ticks a second
		//menu with 8 tiles.. 16 sprites
		//frames this cycle
		
		
		
		int frames = 0;
		while (active)
		{
			long loopStart = System.nanoTime();
			delta = (loopStart - lastLoopTime);
			lastLoopTime = loopStart;
			
			logic(delta);
			
			frames++;
			display.draw();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (System.currentTimeMillis() >= secondTime + 1000)
			{
				this.frames = frames;
				frames = 0;
				secondTime += 1000;
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		boolean active = true;
		
		//frames this cycle
		int frames = 0;
		
		long lastLoopTime = System.currentTimeMillis();
		long secondTime = System.currentTimeMillis();
		
		while (active)
		{
			//if it has been a second since last fps update
			if (System.currentTimeMillis() >= secondTime + 1000)
			{
				State.setFPS(frames);
				frames = 0;
				secondTime = System.currentTimeMillis();
			}
			frames++;
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			
			logic();
			
			display.draw();
			
			
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} **/
	}
	
	public void logic(long delta)
	{
		//Take input
		inputLogic();
		State.update();
		
		//Perform operations
	}
	
	public void inputLogic()
	{
		if (!State.noFocus())
		{
			//If there is a focused object, operate it
			State.peekFocus().input();
		}
		else
		{
			State.getPlayer().input();
			//If there isn't a focused object, do your stuff.
		}
	}
}
