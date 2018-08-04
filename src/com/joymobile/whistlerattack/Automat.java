package com.joymobile.whistlerattack;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

class Automat extends Sprite implements Combo.Listener
{
	AlphaModifier mod;
	float before = 1;
	
	public Automat( int pX, int pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pSpriteVertexBufferObject )
	{
		super( pX, pY, pTextureRegion, pSpriteVertexBufferObject );
		
		setBlendFunction( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
	}

	public void onComboTick( int counter, boolean is_frozen, TimerHandler timer )
	{
		if ( counter > 15 && before > 0 )
		{
			float after = Math.max( before - ( 0.05f ), 0f );
			mod = new AlphaModifier( Time.get( 0.3f ), before, after );
			registerEntityModifier( mod );
			before = after;
		}
	}

	public void onComboFinish( int counter )
	{
		mod = new AlphaModifier( Time.get( 0.3f ), before, 1 );
		registerEntityModifier( mod );
		before = 1;
	}

	public void onComboFreeze()
	{

	}

	public void onComboUnfreeze()
	{

	}
}