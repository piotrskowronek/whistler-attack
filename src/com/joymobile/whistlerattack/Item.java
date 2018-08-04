package com.joymobile.whistlerattack;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

class Item extends AnimatedSprite
{
	protected static VertexBufferObjectManager VBO;
	
	protected boolean isClicked = false;
	protected State state;
	protected Scene scene;
	protected GameScreen engine;
	protected MainActivity main;
	
	protected float touchX;
	protected float touchY;
	
	TouchArea rect;
	
	public static void setVbo( VertexBufferObjectManager vbo )
	{
		VBO = vbo;
	}
	
	public Item( int x, int y, State state, ITiledTextureRegion texture, Scene scene, GameScreen engine, MainActivity main )
	{
		super( x, y, texture, VBO );
		this.scene = scene;
		this.engine = engine;
		this.main = main;
		changeState( state );
		
		//rect = new TouchArea( x-main.w(0.04), y+main.h(0.05), main.w(0.22), main.h(0.095 * 1.828), main.getVertexBufferObjectManager() );
		rect = new TouchArea( x-main.w(0.04), y+main.h(0.03), main.w(0.22), main.h(0.125 * 1.828), main.getVertexBufferObjectManager() );
		rect.setItem( this );
		rect.setColor(0,0,0);
		rect.setAlpha(0f);
		scene.attachChild( rect );
		scene.registerTouchArea(rect);
	}
	
	@Override
	public void onDetached()
	{
		main.runOnUpdateThread( new Runnable()
		{
			public void run() 
			{
				scene.unregisterTouchArea( rect );
				rect.detachSelf();
			}
		});
	}
	
	public State getState()
	{
		return state;
	}
	
	public void changeState( State state )
	{
		this.state = state;
		this.state.initialize( this, scene, engine, main );
		this.state.onInit();
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	{
		return state.onAreaTouched( pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY );
    }	
	
	public void setAnimation( int state, AnimatedSprite.IAnimationListener callback )
	{
		
	}
	
	public void setAnimation( int state )
	{
		setAnimation( state, null );
	}
	
	class TouchArea extends Rectangle
	{
		Item item;
		
		public TouchArea(float pX, float pY, float width, float height, VertexBufferObjectManager pSpriteVertexBufferObject)
		{
			super(pX, pY, width, height, pSpriteVertexBufferObject);
		}
		
		public void setItem( Item item )
		{
			this.item = item;
		}
		
		@Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		{
			return item.onAreaTouched( pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY );
	    }
	}
}