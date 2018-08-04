package com.joymobile.whistlerattack;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

class ComboText implements Combo.Listener
{
	Sprite great, awesome, fabulous, unbelievable, superr;
	MainActivity main;
	Scene scene;
	
	public ComboText( Scene scene, MainActivity main )
	{
		this.main = main;
		this.scene = scene;
		great = new Sprite( main.w(0.0), main.h(0.7), main.textures.get( "great" ), main.getVertexBufferObjectManager() );
		scene.attachChild( great );
		great.setScale( 0 );
		
		awesome = new Sprite( main.w(0.0), main.h(0.7), main.textures.get( "awesome" ), main.getVertexBufferObjectManager() );
		scene.attachChild( awesome );
		awesome.setScale( 0 );
		
		fabulous = new Sprite( main.w(0.0), main.h(0.7), main.textures.get( "fabulous" ), main.getVertexBufferObjectManager() );
		scene.attachChild( fabulous );
		fabulous.setScale( 0 );
		
		unbelievable = new Sprite( main.w(0.0), main.h(0.7), main.textures.get( "unbelievable" ), main.getVertexBufferObjectManager() );
		scene.attachChild( unbelievable );
		unbelievable.setScale( 0 );
		
		superr = new Sprite( main.w(0.0), main.h(0.7), main.textures.get( "super" ), main.getVertexBufferObjectManager() );
		scene.attachChild( superr );
		superr.setScale( 0 );
	}
	
	public void onComboTick( int counter, boolean is_frozen, TimerHandler timer )
	{
		
	}

	public void onComboFinish(int counter)
	{
		if ( counter >= 90 )
		{
			showComboText( unbelievable );
		}
		else if ( counter >= 50 )
		{
			showComboText( fabulous );
		}
		else if ( counter >= 30 )
		{
			showComboText( awesome );
		}
		else if ( counter >= 20 )
		{
			showComboText( great );
		}
		else if ( counter >= 10 )
		{
			showComboText( superr );	
		}
		else
		{
			return;
		}
		
		main.mCombo.play();
	}

	public void onComboFreeze()
	{
		
	}

	public void onComboUnfreeze()
	{
		
	}
	
	private void showComboText( final Sprite text )
	{
		superr.setScale(0);
		great.setScale(0);
		awesome.setScale(0);
		fabulous.setScale(0);
		unbelievable.setScale(0);
		
		text.registerEntityModifier( new RotationModifier( Time.get(0.5f), 0, 360 ) );
    	text.registerEntityModifier( new ScaleModifier( Time.get(0.5f), 0, 1 )  );
    	
    	text.registerUpdateHandler( new TimerHandler( Time.get(2), false, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				text.setScale(0);
			}
		}));
	}
}