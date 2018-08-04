package com.joymobile.whistlerattack;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

class StarDecoration implements Combo.Listener
{
	float timestamp = Time.get(0.7f);
	
	PauseableTimerHandler timer1;
	PauseableTimerHandler timer2;
	
	public StarDecoration( final MainActivity main, GameScreen screen, final Scene scene )
	{
		timer1 = new PauseableTimerHandler( timestamp, true, new ITimerCallback()
		{
			public void onTimePassed( TimerHandler pTimerHandler )
			{
				final Sprite sprite =
						new Sprite( 0, MainActivity.CAMERA_HEIGHT, main.textures.get( "particle" ), main.getVertexBufferObjectManager() );
				scene.getChildByIndex( 3 ).attachChild( sprite );

				Random rand = new Random();

				sprite.registerEntityModifier( new JumpModifier( Time.get( 3 ), 0, main.w( rand.nextFloat() ) / 2, main.h( 1.0 ), main.h( rand.nextFloat() ), main.h( 0.7 ) ) );
				sprite.registerEntityModifier( new AlphaModifier( Time.get( 2 ), 1.0f, 0.0f ) );
				sprite.registerEntityModifier( new RotationModifier( Time.get( 3 ), 0, rand.nextFloat() * 360 ) );
				// sprite.registerEntityModifier( new ColorModifier( 2,
				// 0.5f, rand.nextFloat(), 0.7f, rand.nextFloat(), 0.2f,
				// rand.nextFloat() ) );

				sprite.registerUpdateHandler( new TimerHandler( 3, false, new ITimerCallback() {
					public void onTimePassed( TimerHandler pTimerHandler )
					{
						main.runOnUpdateThread( new Runnable() {
							public void run()
							{
								sprite.detachSelf();
							}
						} );
					}
				} ) );
			}
		} );
		timer2 = new PauseableTimerHandler( timestamp, true, new ITimerCallback()
		{
			public void onTimePassed( TimerHandler pTimerHandler )
			{
				final Sprite sprite =
						new Sprite( 0, MainActivity.CAMERA_HEIGHT, main.textures.get( "particle" ), main.getVertexBufferObjectManager() );
				scene.getChildByIndex( 3 ).attachChild( sprite );

				Random rand = new Random();

				sprite.registerEntityModifier( new JumpModifier( Time.get( 3 ), main.w( 1.0 ), main.w( 1.0 )
						- main.w( rand.nextFloat() ) / 2, main.h( 1.0 ), main.h( rand.nextFloat() ), main.h( 0.7 ) ) );
				sprite.registerEntityModifier( new AlphaModifier( Time.get( 2 ), 1.0f, 0.0f ) );
				sprite.registerEntityModifier( new RotationModifier( Time.get( 3 ), 0, rand.nextFloat() * 360 ) );
				// sprite.registerEntityModifier( new ColorModifier( 2,
				// 0.5f, rand.nextFloat(), 0.7f, rand.nextFloat(), 0.2f,
				// rand.nextFloat() ) );

				sprite.registerUpdateHandler( new TimerHandler( Time.get( 3 ), false, new ITimerCallback() {
					public void onTimePassed( TimerHandler pTimerHandler )
					{
						main.runOnUpdateThread( new Runnable() {
							public void run()
							{
								sprite.detachSelf();
							}
						} );
					}
				} ) );
			}
		} );
		
		scene.registerUpdateHandler( timer1 );		    	
    	scene.registerUpdateHandler( timer2 );
    	
    	timer1.pause();
    	timer2.pause();
	}
			
	public void onComboTick(int counter, boolean is_frozen, TimerHandler timer)
	{
		timestamp = Math.max( timestamp - 0.05f, 0.2f );
		timer1.setTimerSeconds( timestamp );
		timer2.setTimerSeconds( timestamp );
		if ( counter == 5 )
		{
			timer1.resume();
			timer2.resume();
		}
	}

	public void onComboFinish(int counter)
	{
		timestamp = Time.get(0.7f);
		timer1.pause();
    	timer2.pause();
	}

	public void onComboFreeze() {
		// TODO Auto-generated method stub
		
	}

	public void onComboUnfreeze() {
		// TODO Auto-generated method stub
		
	}
}