package GameAPI.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ScreenKeyListener extends KeyAdapter
{
	private boolean[] keys = new boolean[65883];
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}
	public void keyPressed(KeyEvent e)
	{ 
		keys[e.getKeyCode()] = true;
	}
	
	public boolean isKeyPressed(int key)
	{
		return keys[key];
	}
}