package VolatiliaAPI.game;

import java.awt.Color;
import java.util.ArrayList;

import VolatiliaAPI.entity.Entity;
import VolatiliaAPI.graphics.basic.BasicObject;
import VolatiliaAPI.graphics.basic.Rectangle;
import VolatiliaAPI.map.Map;
import VolatiliaAPI.map.Tile;
import VolatiliaAPI.screen.ScreenManager;
import VolatiliaAPI.util.Location;

public class GameBase
{
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<BasicObject> basic = new ArrayList<BasicObject>();

	private Map map = null;

	private int width, height;

	protected int[] pixels;

	private int xOffset = 0, yOffset = 0;

	public GameBase(int w, int h)
	{
		width = w;
		height = h;
		pixels = new int[width * height];
	}

	public void clearGame()
	{
		entities.clear();
	}

	public void render()
	{
		renderMap();
		renderBasicObjects();
		renderEntities();
	}

	public void update()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			Entity ent = entities.get(i);
			if(ent==null)
				continue;
			ent.update();
			if(!ent.isAlive())
			{
				entities.remove(i);
				i--;
			}
		}
	}

	private void renderMap()
	{
		if(map != null)
			pixels = map.render(xOffset, yOffset, width, height);
	}

	private void renderEntities()
	{
		for(Entity ent: entities)
		{
			if(ent==null)
				continue;
			ent.render();
			int[] pix = ent.getPixels();
			for(int x = 0; x < ent.getImageWidth(); x++)
			{
				for(int y = 0; y < ent.getImageHeight(); y++)
				{
					int yy = (((y + ent.getLocation().getY()) - yOffset) - ((ent.getImageHeight() - ent.getHeight()) / 2));
					int xx = ((x + ent.getLocation().getX()) - xOffset - ((ent.getImageWidth() - ent.getWidth()) / 2));
					if(pix[ent.getImageWidth() * y + x] != ScreenManager.getInstance().getOmmitColor() && yy < height && xx < width)
					{
						pixels[width * yy  + xx] = pix[ent.getImageWidth() * y + x];
					}
				}
			}
		}
	}
	
	public void renderBasicObjects()
	{
		for (BasicObject bo : basic)
		{
			int[] image = bo.getPixles();

			for (int y = 0; y < bo.getHeight(); y++)
			{
				for (int x = 0; x < bo.getWidth(); x++)
				{
					if(x < 0 || x + bo.getX() > width-1 || y < 0 || y + bo.getY() > height-1)break;
					if (image[x + y * bo.getWidth()] != ScreenManager.getInstance().getOmmitColor())
						pixels[width * (y + bo.getY()) + (x + bo.getX())] = image[x + y * bo.getWidth()];
				}
			}
		}
	}
	
	public void clearPixels()
	{
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
					pixels[width * y + x] = 0x000000;
			}
		}
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	public void addEntity(Entity ent)
	{
		entities.add(ent);
	}

	public boolean canMoveTo(Location loc, int size)
	{
		if(map.getTileAt(new Location(loc.getX() / Tile.SIZE, loc.getY() / Tile.SIZE)).solid())
		{
			return false;
		}
		else if(map.getTileAt(new Location((loc.getX() + size) / Tile.SIZE, loc.getY() / Tile.SIZE)).solid())
		{
			return false;
		}
		else if(map.getTileAt(new Location(loc.getX() / Tile.SIZE, (loc.getY() + size) / Tile.SIZE)).solid())
		{
			return false;
		}
		else if(map.getTileAt(new Location((loc.getX() + size) / Tile.SIZE, (loc.getY() + size) / Tile.SIZE)).solid())
		{
			return false;
		}
		return true;
	}

	public void setOffset(int xoff, int yoff)
	{
		xOffset = xoff;
		yOffset = yoff;
	}

	public Map getCurrentMap()
	{
		return map;
	}
	public void setCurrentMap(Map nextmap)
	{
		map = nextmap;
		map.generate();
	}

	public int[] getPixels()
	{
		return pixels.clone();
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	
	public Rectangle addRectangle(int x, int y, int w, int h, Color c)
	{
		Rectangle rect = new Rectangle(x, y, w, h, c);
		basic.add(rect);
		return rect;
	}
	
	public void addBasicObject(BasicObject obj)
	{
		basic.add(obj);
	}
	
	public void clearBasicObjects()
	{
		basic.clear();
	}
	
	public ArrayList<BasicObject> getBasicObjects()
	{
		return basic;
	}
}
