package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class Star extends Item
{
	protected static ITiledTextureRegion TEXTURE;
	public final static int TICK = 327654228;
	public final static int MISS = 164444521;
	public final static int OPEN = 222486421;
	
	public static void setTexture( ITiledTextureRegion texture )
	{
		TEXTURE = texture;
	}
	
	public Star( int x, int y, Scene scene, GameScreen engine, MainActivity main )
	{
		super( x, y, new ClosedState(), TEXTURE, scene, engine, main );
	}
	
	@Override
	public void setAnimation( int state, AnimatedSprite.IAnimationListener callback )
	{
		switch ( state )
		{
			case State.OPENED:
				setCurrentTileIndex( 0 );
				break;
			case State.OPENING:
				animate( (new long[]{30,30,30,30,30,30,30,30,30}), 7, 15, 0, callback );
				break;
			case State.MISSED_CLOSING:
				animate( (new long[]{30,30,30,30,30,30,30,30,30}), 0, 8, 0, callback );
				break;
			case State.OWNED:
				animate( (new long[]{15,15,15,15,15,15,15,15,15}), 0, 8, 0, callback );
				break;
			case State.CLOSED:
				setCurrentTileIndex( 7 );
				break;
		}
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	{
		super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		
		return true;
	}
}