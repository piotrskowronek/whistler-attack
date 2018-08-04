package com.joymobile.whistlerattack;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.entity.sprite.Sprite;

public class Life
{
	static Life instance;
	GameScreen screen;
	public final static int GAMEOVER = 79543544;
	protected ArrayList<Unit.Listener> unit_listeners = new ArrayList<Unit.Listener>();
	
	Stack<Sprite> hearts = new Stack<Sprite>();
	int lives;
	boolean enabled = true;
	
	public static Life get()
	{
		if ( instance == null )
		{
			instance = new Life();
		}
		return instance;
	}
	
	private Life()
	{
		lives = 5;
	}
	
	public void addUnitListener( Unit.Listener listener )
    {
        unit_listeners.add( listener );
    }
	
	public void disable()
	{
		enabled = false;
	}
	
	public void enable()
	{
		enabled = true;
	}
	
	public void setGameScreen( GameScreen screen )
	{
		this.screen = screen;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public void registerHeartSprite( Sprite heart )
	{
		hearts.push( heart );
	}
	
	public void addLife()
	{
		if ( lives > 0 )
		{
			lives++;
			
			Sprite last_heart = hearts.lastElement();
			float x = last_heart.getX();
			float y = last_heart.getY();
			x -= screen.main.h(0.15f);
			
			Sprite heart = new Sprite( x, y, screen.main.textures.get( "heart" ), screen.main.getVertexBufferObjectManager() );
			screen.main.scene.attachChild( heart );
			registerHeartSprite( heart );
		}
	}
	
	public void reset()
	{
		lives = 5;
		unit_listeners = new ArrayList<Unit.Listener>();
	}
	
	public void kill()
	{
		if ( enabled )
		{
			lives--;
			
			screen.main.runOnUpdateThread( new Runnable()
			{
				public void run() 
				{
					if ( ! hearts.isEmpty() )
					{
						hearts.lastElement().detachSelf();
						hearts.pop();
					}
				}
			});
			
			if ( lives == 0 )
			{
				for ( Unit.Listener listener : unit_listeners )
				{
					if ( listener.hasPermission( Life.GAMEOVER ) )
					{
						listener.sendUnit( Life.GAMEOVER, new Unit() );
					}
				}
				
				screen.endGame();
			}
		}
	}
}