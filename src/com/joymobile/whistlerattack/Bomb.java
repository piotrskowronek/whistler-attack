package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class Bomb extends Item
{
	protected static ITiledTextureRegion TEXTURE;
	public final static int OPEN = 345876214;
	public final static int TICK = 487755123;
	
	public static void setTexture( ITiledTextureRegion texture )
	{
		TEXTURE = texture;
	}
	
	public Bomb( int x, int y, Scene scene, GameScreen engine, MainActivity main )
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
				animate( Time.get(new long[]{30,30,30,30,30,30,30,30,30}), 7, 15, 0, callback );
				break;
			case State.MISSED_CLOSING:
				animate( Time.get(new long[]{15,15,15,15,15,15,15,15,15}), 0, 8, 0, callback );
				break;
			case State.OWNED:
				animate(Time.get( new long[]{15,15,15,15,15,15,15,15,15}), 0, 8, 0, callback );
				break;
			case State.CLOSED:
				setCurrentTileIndex( 7 );
				break;
		}
	}
}