package GameAPI.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ScreenKeyListener extends KeyAdapter
{
	private static boolean[] keys = new boolean[65883];
	
	public static int lastPressed = -1;
	
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}
	public void keyPressed(KeyEvent e)
	{ 
		keys[e.getKeyCode()] = true;
		lastPressed = e.getKeyCode();
	}
	
	public static boolean isKeyPressed(int key)
	{
		return keys[key];
	}
}