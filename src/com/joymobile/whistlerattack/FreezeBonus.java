package com.joymobile.whistlerattack;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

class FreezeBonus extends Item
{
	protected static ITiledTextureRegion TEXTURE;
	
	public static void setTexture( ITiledTextureRegion texture )
	{
		TEXTURE = texture;
	}
	
	public FreezeBonus( int x, int y, Scene scene, GameScreen engine, MainActivity main )
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
}